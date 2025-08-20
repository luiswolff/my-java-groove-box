package groovebox.ui;

import java.util.Arrays;
import java.util.List;

import groovebox.service.Beat;
import groovebox.service.Phrase;
import groovebox.service.SampleBeatFactory;
import groovebox.service.TickPosition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

class GrooveBoxModel {
	private final BooleanProperty trackIsPlaying = new SimpleBooleanProperty(false);
	private final ObjectProperty<Beat> beat = new SimpleObjectProperty<>();
	private final ObjectProperty<Phrase> phrase = new SimpleObjectProperty<>();
	private final ListProperty<Phrase> phrases = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty phraseIndex = new SimpleIntegerProperty();
	private final ObjectProperty<Integer> tempo = new SimpleObjectProperty<>();
	private final BooleanProperty infinity = new SimpleBooleanProperty(false);
	private final ObjectProperty<Integer> loopCount = new SimpleObjectProperty<>();
	private final ListProperty<SampleBeatFactory> sampleBeatFactories = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty highlightedTick = new SimpleIntegerProperty(-1);
	private final Runnable modelChangedCallback;

	GrooveBoxModel(Runnable modelChangedCallback) {
		this.modelChangedCallback = modelChangedCallback;
		phraseIndex.addListener((observable, oldValue, nuewValue) -> onPhraseIndexChanged());
		beat.addListener((observable, oldValue, newValue) -> onBeatChanged());
		tempo.addListener((observable, oldValue, newValue) -> tempoChanged());
		loopCount.addListener((observable, oldValue, newValue) -> loopCountChanged());
		infinity.addListener((observable, oldValue, newValue) -> infinityChanged());

		Arrays.stream(SampleBeatFactory.values()).forEach(this::addSampleBeatFactory);
	}

	private void infinityChanged() {
		if (infinity.get()) {
			beat.get().setLoopCount(-1);
		} else {
			beat.get().setLoopCount(loopCount.get() - 1);
		}
		modelChangedCallback.run();
	}

	private void loopCountChanged() {
		if (!infinity.get()) {
			beat.get().setLoopCount(loopCount.get() - 1);
			modelChangedCallback.run();
		}
	}

	private void tempoChanged() {
		beat.get().setTempoInBPM(tempo.get());
		modelChangedCallback.run();
	}

	private void onPhraseIndexChanged() {
		int index = phraseIndex.get();
		phrase.set(phrases.get(index));
	}

	private void onBeatChanged() {
		phraseIndex.setValue(0);
		phrases.setValue(FXCollections.observableList(getFourBarPhrasesFromBeat()));
		phrase.setValue(phrases.get(phraseIndex.get()));
		tempo.setValue((int) beat.get().getTempoInBPM());
		infinity.setValue(beat.get().isInfinityLoopCount());
		loopCount.setValue(Math.max(1, beat.get().getLoopCount() + 1));
	}

	private List<Phrase> getFourBarPhrasesFromBeat() {
		List<Phrase> phrases = beat.get().getPhrases();
		if (phrases.isEmpty()) {
			phrases.add(new Phrase());
		}
		return phrases;
	}

	BooleanProperty trackIsPlayingProperty() {
		return trackIsPlaying;
	}

	ObjectProperty<Beat> beatProperty() {
		return beat;
	}

	ObjectProperty<Phrase> phraseProperty() {
		return phrase;
	}

	ListProperty<Phrase> phrasesProperty() {
		return phrases;
	}

	IntegerProperty phraseIndexProperty() {
		return phraseIndex;
	}

	ObjectProperty<Integer> tempoProperty() {
		return tempo;
	}

	BooleanProperty infinityProperty() {
		return infinity;
	}

	ObjectProperty<Integer> loopCountProperty() {
		return loopCount;
	}

	ListProperty<SampleBeatFactory> sampleBeatFactoriesProperty() {
		return sampleBeatFactories;
	}

	IntegerProperty highlightedTickProperty() {
		return highlightedTick;
	}

	void trackIsPlaying() {
		trackIsPlaying.setValue(true);
	}

	void trackIsPaused() {
		trackIsPlaying.setValue(false);
		highlightedTick.setValue(-1);
	}

	void setBeat(Beat beat) {
		beatProperty().set(beat);
	}

	void addSampleBeatFactory(SampleBeatFactory sampleBeatFactory) {
		sampleBeatFactories.add(sampleBeatFactory);
	}

	void setHighlightedTick(TickPosition tickPosition) {
		if (phraseIndex.get() == tickPosition.currentPhrase()) {
			highlightedTick.setValue(tickPosition.phrasePosition());
		} else {
			highlightedTick.setValue(-1);
		}
	}

	void addNewPhrase() {
		phrases.add(new Phrase());
		phraseIndex.set(phrases.size() - 1);
	}

	void removeCurrentPhrase() {
		phrases.remove(phraseIndex.get());
		if (phrases.isEmpty()) {
			Phrase element = new Phrase();
			phrases.add(element);
			phrase.setValue(element);
		} else {
			phraseIndex.subtract(1);
		}
	}
}
