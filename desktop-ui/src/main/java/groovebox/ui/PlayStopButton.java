package groovebox.ui;

import groovebox.ui.model.GrooveBoxModel;
import javafx.scene.control.Button;

public class PlayStopButton extends Button {
	public PlayStopButton() {
		setGraphic(Icons.play());
	}
	void apply(GrooveBoxModel model) {
		model.trackIsPlayingProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				setGraphic(Icons.stop());
			} else {
				setGraphic(Icons.play());
			}
		});
	}
}
