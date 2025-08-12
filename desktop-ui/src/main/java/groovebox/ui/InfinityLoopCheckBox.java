package groovebox.ui;

import javafx.scene.control.CheckBox;

public class InfinityLoopCheckBox extends CheckBox {
	void apply(GrooveBoxModel model) {
		selectedProperty().bindBidirectional(model.infinityProperty());
	}
}
