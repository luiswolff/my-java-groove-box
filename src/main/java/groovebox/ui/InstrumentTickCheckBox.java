package groovebox.ui;

import java.util.List;

import groovebox.model.Instrument;
import groovebox.model.Tick;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.CheckBox;

class InstrumentTickCheckBox extends CheckBox {
	final IntegerProperty velocity = new SimpleIntegerProperty(100);

	InstrumentTickCheckBox(Instrument value, List<Tick> ticks) {
		Tick tick = getTick(ticks, new Tick(value, velocity.getValue()));
		selectedProperty().addListener((event, oldValue, newValue) -> {
			if (newValue) {
				ticks.add(tick);
			} else {
				ticks.remove(tick);
			}
		});
		velocity.addListener((event, oldValue, newValue) -> tick.setVelocity(velocity.getValue()));
	}

	private Tick getTick(List<Tick> ticks, Tick tick) {
		if (ticks.contains(tick)) {
			Tick existingTick = ticks.get(ticks.indexOf(tick));
			selectedProperty().setValue(true);
			velocity.setValue(existingTick.getVelocity());
			return existingTick;
		}
		return tick;
	}

}
