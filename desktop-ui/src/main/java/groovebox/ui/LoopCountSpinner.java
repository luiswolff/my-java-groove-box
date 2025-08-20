package groovebox.ui;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class LoopCountSpinner extends Spinner<Integer> {

	public LoopCountSpinner() {
		setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
	}

	void apply(GrooveBoxModel model) {
		getValueFactory().valueProperty().bindBidirectional(model.loopCountProperty());
		disableProperty().bind(model.trackIsPlayingProperty().or(model.infinityProperty()));
	}
}
