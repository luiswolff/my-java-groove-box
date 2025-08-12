package groovebox.ui;

import java.util.Arrays;

import groovebox.model.Beat;
import groovebox.model.FourBarPhrase;
import groovebox.model.SampleBeatFactory;
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
	private final ObjectProperty<FourBarPhrase> phrase = new SimpleObjectProperty<>();
	private final BooleanProperty infinity = new SimpleBooleanProperty(false);
	private final ListProperty<SampleBeatFactory> sampleBeatFactories = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final IntegerProperty highlightedTick = new SimpleIntegerProperty(-1);

	GrooveBoxModel() {
		beat.addListener((observable, oldValue, newValue) -> phrase.setValue(getFirstPhrase()));
		beat.addListener((observable, oldValue, newValue) -> infinity.setValue(beat.get().isInfinityLoopCount()));

		setBeat(new Beat());
		Arrays.stream(SampleBeatFactory.values()).forEach(this::addSampleBeatFactory);
	}

	private FourBarPhrase getFirstPhrase() {
		return beat.get().getPhrases().getFirst();
	}

	ObjectProperty<Node> playButtonGraphicProperty() {
		return playButtonGraphic;
	}

	ObjectProperty<Beat> beatProperty() {
		return beat;
	}

	ObjectProperty<FourBarPhrase> phraseProperty() {
		return phrase;
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

	void setHighlightedTick(int tick) {
		highlightedTick.setValue(tick);
	}
}
