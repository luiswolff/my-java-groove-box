package groovebox.ui;

import java.util.Arrays;

import groovebox.service.Beat;
import groovebox.service.BeatSamples;
import groovebox.service.Phrase;
import groovebox.service.SoundControl;
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
import javafx.collections.ObservableList;

class GrooveBoxModel {
	private final BooleanProperty trackIsPlaying = new SimpleBooleanProperty(false);
	private final ObjectProperty<Beat<ObservableList<Phrase>>> beat = new SimpleObjectProperty<>();
	private final ObjectProperty<Phrase> phrase = new SimpleObjectProperty<>();
	private final ListProperty<Phrase> phrases = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty phraseIndex = new SimpleIntegerProperty();
	private final ObjectProperty<Integer> tempo = new SimpleObjectProperty<>();
	private final BooleanProperty infinity = new SimpleBooleanProperty(false);
	private final ObjectProperty<Integer> loopCount = new SimpleObjectProperty<>();
	private final ListProperty<BeatSamples> sampleBeatFactories = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty highlightedTick = new SimpleIntegerProperty(-1);

	private final SoundControl soundControl;

	GrooveBoxModel(SoundControl soundControl) {
		phraseIndex.addListener((observable, oldValue, nuewValue) -> onPhraseIndexChanged());
		beat.addListener((observable, oldValue, newValue) -> onBeatChanged());
		tempo.addListener((observable, oldValue, newValue) -> tempoChanged());
		loopCount.addListener((observable, oldValue, newValue) -> loopCountChanged());
		infinity.addListener((observable, oldValue, newValue) -> infinityChanged());

		Arrays.stream(BeatSamples.values()).forEach(this::addSampleBeatFactory);

		this.soundControl = soundControl;
		setBeat(new Beat<>(FXCollections::observableArrayList));
	}

	private void infinityChanged() {
		soundControl.setLoopContinuously(infinity.get());
	}

	private void loopCountChanged() {
		soundControl.setLoopCount(loopCount.get());
	}

	private void tempoChanged() {
		soundControl.setTempoInBPM(tempo.get());
	}

	private void onPhraseIndexChanged() {
		int index = phraseIndex.get();
		phrase.set(phrases.get(index));
	}

	private void onBeatChanged() {
		phraseIndex.setValue(0);
		phrases.setValue(beat.get().getPhrases());
		phrase.setValue(phrases.get(phraseIndex.get()));
		tempo.setValue(soundControl.getTempoInBPM());
		infinity.setValue(soundControl.isLoopContinuously());
		loopCount.setValue(soundControl.getLoopCount());
	}

	BooleanProperty trackIsPlayingProperty() {
		return trackIsPlaying;
	}

	ObjectProperty<Beat<ObservableList<Phrase>>> beatProperty() {
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

	ListProperty<BeatSamples> sampleBeatFactoriesProperty() {
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

	void setBeat(Beat<ObservableList<Phrase>> beat) {
		beatProperty().set(beat);
	}

	void addSampleBeatFactory(BeatSamples sampleBeatFactory) {
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
		beat.get().addPhrase();
		phraseIndex.set(phrases.size() - 1);
	}

	void removeCurrentPhrase() {
		beat.get().removePhrase(phraseIndex.get());
		if (phrases.isEmpty()) {
			beat.get().addPhrase();
		} else {
			phraseIndex.subtract(1);
		}
	}
}
