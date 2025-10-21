package groovebox.ui;

import groovebox.ui.model.ShownInstrumentTickModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

class InstrumentTickCheckBox extends CheckBox {
	final ObjectProperty<Integer> velocity = new SimpleObjectProperty<>();

	public InstrumentTickCheckBox(ShownInstrumentTickModel tick) {
		selectedProperty().bindBidirectional(tick.isActiveProperty());
		velocityProperty().bindBidirectional(tick.velocityProperty());
		setContextMenu(createContextMenu());
	}

	private ContextMenu createContextMenu() {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem velocityMenuItem = new MenuItem("Velocity");
		velocityMenuItem.setOnAction(event -> ChangeVelocityDialog.editVelocity(velocity));
		contextMenu.getItems().add(velocityMenuItem);
		return contextMenu;
	}

	ObjectProperty<Integer> velocityProperty() {
		return velocity;
	}

}
