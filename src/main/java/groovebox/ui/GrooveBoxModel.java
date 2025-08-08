package groovebox.ui;

import groovebox.model.Beat;
import groovebox.model.FourBarPhrase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

class GrooveBoxModel {
	private final ObjectProperty<Node> playButtonGraphic = new SimpleObjectProperty<>(Icons.play());
	private final ObjectProperty<Beat> beat = new SimpleObjectProperty<>();
	private final ObjectProperty<FourBarPhrase> phrase = new SimpleObjectProperty<>();
	private final BooleanProperty infinity = new SimpleBooleanProperty(false);

	GrooveBoxModel() {
		beat.addListener((observable, oldValue, newValue) -> phrase.setValue(getFirstPhrase()));
		beat.addListener((observable, oldValue, newValue) -> infinity.setValue(beat.get().isInfinityLoopCount()));
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

	void trackIsPlaying() {
		playButtonGraphic.setValue(Icons.stop());
	}

	void trackIsPaused() {
		playButtonGraphic.setValue(Icons.play());
	}
}
