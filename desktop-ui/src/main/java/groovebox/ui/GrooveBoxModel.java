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
import javafx.scene.Node;

class GrooveBoxModel {
	private final ObjectProperty<Node> playButtonGraphic = new SimpleObjectProperty<>(Icons.play());
	private final ObjectProperty<Beat> beat = new SimpleObjectProperty<>();
	private final ObjectProperty<Phrase> phrase = new SimpleObjectProperty<>();
	private final ListProperty<Phrase> phrases = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty phraseIndex = new SimpleIntegerProperty();
	private final BooleanProperty infinity = new SimpleBooleanProperty(false);
	private final ListProperty<SampleBeatFactory> sampleBeatFactories = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty highlightedTick = new SimpleIntegerProperty(-1);

	GrooveBoxModel() {
		phraseIndex.addListener((observable, oldValue, nuewValue) -> onPhraseIndexChanged());
		beat.addListener((observable, oldValue, newValue) -> onBeatChanged());

		setBeat(new Beat());
		Arrays.stream(SampleBeatFactory.values()).forEach(this::addSampleBeatFactory);
	}

	private void onPhraseIndexChanged() {
		int index = phraseIndex.get();
		phrase.set(phrases.get(index));
	}

	private void onBeatChanged() {
		phraseIndex.setValue(0);
		phrases.setValue(FXCollections.observableList(getFourBarPhrasesFromBeat()));
		phrase.setValue(phrases.get(phraseIndex.get()));
		infinity.setValue(beat.get().isInfinityLoopCount());
	}

	private List<Phrase> getFourBarPhrasesFromBeat() {
		List<Phrase> phrases = beat.get().getPhrases();
		if (phrases.isEmpty()) {
			phrases.add(new Phrase());
		}
		return phrases;
	}

	ObjectProperty<Node> playButtonGraphicProperty() {
		return playButtonGraphic;
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

	BooleanProperty infinityProperty() {
		return infinity;
	}

	ListProperty<SampleBeatFactory> sampleBeatFactoriesProperty() {
		return sampleBeatFactories;
	}

	IntegerProperty highlightedTickProperty() {
		return highlightedTick;
	}

	void trackIsPlaying() {
		playButtonGraphic.setValue(Icons.stop());
	}

	void trackIsPaused() {
		playButtonGraphic.setValue(Icons.play());
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
