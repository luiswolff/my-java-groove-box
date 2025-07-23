package groovebox.ui;

import groovebox.model.Beat;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TempoSpinner extends Spinner<Integer> {

	void defineBeat(Beat beat, GrooveBoxController grooveBoxController) {
		SpinnerValueFactory.IntegerSpinnerValueFactory value = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 300,
				(int) beat.getTempoInBPM());
		value.valueProperty().addListener((observable, oldValue, newValue) -> {
			beat.setTempoInBPM(newValue);
			grooveBoxController.handleModelChanged();
		});
		setValueFactory(value);

	}
}
