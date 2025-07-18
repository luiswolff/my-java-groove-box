package groovebox.ui;

import java.util.Optional;

import groovebox.model.Beat;
import groovebox.model.Instrument;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class InstrumentTickCheckBox extends CheckBox {
	void applyInstrumentChange(Beat beat) {
		int row = Optional.ofNullable(GridPane.getRowIndex(this)).orElse(0);
		int col = Optional.ofNullable(GridPane.getColumnIndex(this)).orElse(0) - 1;

		beat.defineTick(Instrument.values()[row], isSelected(), col / 4, col % 4);
	}
}
