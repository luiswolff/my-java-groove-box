package groovebox.ui;

import groovebox.model.Beat;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class LoopCountSpinner extends Spinner<Integer> {

	void defineBeat(Beat beat, GrooveBoxController grooveBoxController) {
		SpinnerValueFactory.IntegerSpinnerValueFactory value = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 100, beat.getLoopCount());
		value.valueProperty().addListener((observable, oldValue, newValue) -> {
			beat.setLoopCount(newValue);
			grooveBoxController.handleModelChanged();
		});
		setValueFactory(value);

	}
}
