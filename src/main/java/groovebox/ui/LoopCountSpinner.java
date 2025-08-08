package groovebox.ui;

import groovebox.model.Beat;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class LoopCountSpinner extends Spinner<Integer> {

	private final ObjectProperty<Beat> beat = new SimpleObjectProperty<>();
	private Runnable changeCallback = null;

	public LoopCountSpinner() {
		beat.addListener((observable, oldValue, newValue) -> defineBeat());
	}

	private void defineBeat() {
		int initialLoopCount = beat.get().isInfinityLoopCount() ? 1 : beat.get().getLoopCount() + 1;
		SpinnerValueFactory.IntegerSpinnerValueFactory value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialLoopCount);
		value.valueProperty().addListener((observable, oldValue, newValue) -> {
			beat.get().setLoopCount(getLoopCount());
			changeCallback.run();
		});
		setValueFactory(value);
		disableProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				beat.get().setLoopCount(-1);
			}  else {
				beat.get().setLoopCount(getLoopCount());
			}
			changeCallback.run();
		});
	}

	private Integer getLoopCount() {
		return getValue() - 1;
	}

	ObjectProperty<Beat> beatProperty() {
		return beat;
	}

	void setChangeCallback(Runnable changeCallback) {
		this.changeCallback = changeCallback;
	}
}
