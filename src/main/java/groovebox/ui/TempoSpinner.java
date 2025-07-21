package groovebox.ui;

import groovebox.model.Beat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class TempoSpinner extends Spinner<Integer> {
	private EventHandler<ActionEvent> onAction;

	public TempoSpinner() {
		super();
		SpinnerValueFactory.IntegerSpinnerValueFactory value = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 300, 94);
		value.valueProperty().addListener((observable, oldValue, newValue) -> onAction.handle(new ActionEvent(this, null)));
		setValueFactory(value);
	}

	void applyChange(Beat beat) {
		beat.setTempoInBPM(getValue());
	}

	public void setOnAction(EventHandler<ActionEvent> handler) {
		this.onAction = handler;
	}

	public EventHandler<ActionEvent> getOnAction() {
		return onAction;
	}
}
