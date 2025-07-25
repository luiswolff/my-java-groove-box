package groovebox.ui;

import groovebox.model.Beat;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class LoopCountSpinner extends Spinner<Integer> {

	void defineBeat(Beat beat, GrooveBoxController grooveBoxController) {
		int initialLoopCount = beat.isInfinityLoopCount() ? 1 : beat.getLoopCount() + 1;
		SpinnerValueFactory.IntegerSpinnerValueFactory value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialLoopCount);
		value.valueProperty().addListener((observable, oldValue, newValue) -> {
			beat.setLoopCount(getLoopCount());
			grooveBoxController.handleModelChanged();
		});
		setValueFactory(value);
		disableProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				beat.setLoopCount(-1);
			}  else {
				beat.setLoopCount(getLoopCount());
			}
			grooveBoxController.handleModelChanged();
		});
	}

	private Integer getLoopCount() {
		return getValue() - 1;
	}
}
