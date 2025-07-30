package groovebox.ui;

import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import groovebox.model.Tick;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;

class InstrumentTickCheckBox extends CheckBox {

	public InstrumentTickCheckBox(Instrument value, QuarterNote quarterNote, int tickIndex) {
		BooleanProperty selectedProperty = selectedProperty();
		selectedProperty.setValue(quarterNote.hasTick(new Tick(value, 120), tickIndex));
		selectedProperty.addListener((event, oldValue, newValue) -> {
			if (newValue) {
				quarterNote.setTick(new Tick(value, 120), tickIndex);
			} else {
				quarterNote.removeTick(new Tick(value, 120), tickIndex);
			}
		});
	}
}
