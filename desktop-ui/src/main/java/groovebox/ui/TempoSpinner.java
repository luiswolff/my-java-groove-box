package groovebox.ui;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TempoSpinner extends Spinner<Integer> {

	public TempoSpinner() {
		setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 300));
	}

	void apply(GrooveBoxModel model) {
		getValueFactory().valueProperty().bindBidirectional(model.tempoProperty());
		disableProperty().bind(model.trackIsPlayingProperty());
	}
}
