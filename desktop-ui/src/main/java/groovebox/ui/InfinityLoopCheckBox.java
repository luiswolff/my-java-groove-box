package groovebox.ui;

import groovebox.ui.model.GrooveBoxModel;
import javafx.scene.control.CheckBox;

public class InfinityLoopCheckBox extends CheckBox {
	void apply(GrooveBoxModel model) {
		selectedProperty().bindBidirectional(model.infinityProperty());
		disableProperty().bind(model.trackIsPlayingProperty());
	}
}
