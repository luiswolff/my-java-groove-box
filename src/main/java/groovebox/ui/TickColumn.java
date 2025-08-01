package groovebox.ui;

import java.util.List;
import java.util.Optional;

import groovebox.model.Instrument;
import groovebox.model.Tick;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.shape.Rectangle;

class TickColumn {
	private final List<Tick> ticks;

	TickColumn(List<Tick> ticks) {
		this.ticks = ticks;
	}

	InstrumentTickCheckBox createCheckBox(Instrument instrument, Rectangle rectangle, GrooveBoxController grooveBoxController) {
		InstrumentTickCheckBox checkBox = new InstrumentTickCheckBox(instrument, ticks);
		checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> grooveBoxController.handleModelChanged());
		checkBox.velocity.addListener((obs, oldVal, newVal) -> grooveBoxController.handleModelChanged());
		ContextMenu contextMenu = new ContextMenu();
		MenuItem velocityMenuItem = new MenuItem("Velocity");
		velocityMenuItem.setOnAction(event -> {
			Dialog<Integer> velocityDialog = new Dialog<>();
			velocityDialog.setTitle("Velocity");
			velocityDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			Spinner<Integer> content = new Spinner<>();
			SpinnerValueFactory.IntegerSpinnerValueFactory value = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 127, checkBox.velocity.getValue());
			content.setValueFactory(value);
			velocityDialog.getDialogPane().setContent(content);
			velocityDialog.setResultConverter(b -> ButtonType.OK.equals(b) ? content.getValue() : null);
			Optional<Integer> velocityResult = velocityDialog.showAndWait();
			velocityResult.ifPresent(velocity -> {
				rectangle.setWidth(velocity * 25.0 / 127.0); // TODO Use property listener
				checkBox.velocity.setValue(velocity);
			});

		});
		contextMenu.getItems().add(velocityMenuItem);
		checkBox.setContextMenu(contextMenu);

		return checkBox;
	}
}
