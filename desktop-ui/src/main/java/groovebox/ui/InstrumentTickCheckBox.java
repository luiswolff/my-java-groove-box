package groovebox.ui;

import groovebox.service.InstrumentDataApi;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

class InstrumentTickCheckBox extends CheckBox {
	final IntegerProperty velocity = new SimpleIntegerProperty(100);

	InstrumentTickCheckBox(InstrumentDataApi instrumentDataApi) {
		selectedProperty().setValue(instrumentDataApi.isActive());
		velocity.setValue(instrumentDataApi.getVelocity());
		selectedProperty().addListener((event, oldValue, newValue) -> instrumentDataApi.setActive(newValue));
		velocityProperty().addListener((event, oldValue, newValue) -> instrumentDataApi.setVelocity(velocity.getValue()));
		setContextMenu(createContextMenu());
	}

	private ContextMenu createContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem velocityMenuItem = new MenuItem("Velocity");
		velocityMenuItem.setOnAction(event -> ChangeVelocityDialog.editVelocity(velocity));
		contextMenu.getItems().add(velocityMenuItem);
		return contextMenu;
	}

	IntegerProperty velocityProperty() {
		return velocity;
	}

}
