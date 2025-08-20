package groovebox.ui;

import groovebox.service.Beat;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TempoSpinner extends Spinner<Integer> {

	private final ObjectProperty<Beat> beat = new SimpleObjectProperty<>();
	private Runnable changeCallback = null;

	public TempoSpinner() {
		beat.addListener((observable, oldValue, newValue) -> defineBeat());
	}

	private void defineBeat() {
		SpinnerValueFactory.IntegerSpinnerValueFactory value = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 300,
				(int) beat.get().getTempoInBPM());
		value.valueProperty().addListener((observable, oldValue, newValue) -> {
			beat.get().setTempoInBPM(newValue);
			changeCallback.run();
		});
		setValueFactory(value);
	}

	ObjectProperty<Beat> beatProperty() {
		return beat;
	}

	void apply(GrooveBoxModel model, Runnable changeCallback) {
		this.changeCallback = changeCallback;
		beatProperty().bind(model.beatProperty());
		disableProperty().bind(model.trackIsPlayingProperty());
	}
}
