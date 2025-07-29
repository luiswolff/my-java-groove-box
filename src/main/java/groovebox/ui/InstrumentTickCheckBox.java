package groovebox.ui;

import groovebox.model.Beat;
import groovebox.model.Instrument;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;

class InstrumentTickCheckBox extends CheckBox {
	InstrumentTickCheckBox(Beat beat, GrooveBoxController grooveBoxController, int col, int row) {
		int noteIndex = col / 4;
		int tickIndex = col % 4;

		setOnAction(event -> {
			if (isSelected()) {
				beat.defineTick(Instrument.values()[row], noteIndex, tickIndex);
			} else {
				beat.removeTick(Instrument.values()[row], noteIndex, tickIndex);
			}
			grooveBoxController.handleModelChanged();
		});

		selectedProperty().bindBidirectional(new SimpleBooleanProperty(beat.hasTick(Instrument.values()[row], noteIndex, tickIndex)));
	}
}
