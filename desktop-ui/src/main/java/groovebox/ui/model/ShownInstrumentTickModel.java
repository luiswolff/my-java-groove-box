package groovebox.ui.model;

import java.util.Arrays;

import groovebox.service.InstrumentDataApi;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Subscription;

public class ShownInstrumentTickModel {
	private final BooleanProperty isActive;
	private final ObjectProperty<Integer> velocity;
	private final Subscription[] subscriptions;

	public ShownInstrumentTickModel(InstrumentDataApi instrumentData) {
		isActive = new SimpleBooleanProperty(instrumentData.isActive());
		velocity = new SimpleObjectProperty<>(instrumentData.getVelocity());

		subscriptions = new Subscription[] {
				isActive.subscribe(instrumentData::setActive),
				velocity.subscribe(instrumentData::setVelocity)
		};
	}

	public BooleanProperty isActiveProperty() {
		return isActive;
	}

	public ObjectProperty<Integer> velocityProperty() {
		return velocity;
	}

	public void destroy() {
		Arrays.stream(subscriptions).forEach(Subscription::unsubscribe);
	}
}
