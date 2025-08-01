package groovebox.ui;

import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import groovebox.model.Tick;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.CheckBox;

class InstrumentTickCheckBox extends CheckBox {
	final IntegerProperty velocity = new SimpleIntegerProperty(100);

	public InstrumentTickCheckBox(Instrument value, QuarterNote quarterNote, int tickIndex) {
		Tick tick = getTick(quarterNote, tickIndex, new Tick(value, velocity.getValue()));
		selectedProperty().addListener((event, oldValue, newValue) -> {
			if (newValue) {
				quarterNote.setTick(tick, tickIndex);
			} else {
				quarterNote.removeTick(tick, tickIndex);
			}
		});
		velocity.addListener((event, oldValue, newValue) -> tick.setVelocity(velocity.getValue()));
	}

	private Tick getTick(QuarterNote quarterNote, int tickIndex, Tick tick) {
		Tick existingTick = quarterNote.getTick(tick, tickIndex);
		if (existingTick != null) {
			selectedProperty().setValue(true);
			velocity.setValue(existingTick.getVelocity());
			return existingTick;
		}
		return tick;
	}

}
