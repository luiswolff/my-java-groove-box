package groovebox.ui;

import javafx.scene.control.Button;

public class PlayStopButton extends Button {
	void apply(GrooveBoxModel model) {
		graphicProperty().bind(model.playButtonGraphicProperty());
	}
}
