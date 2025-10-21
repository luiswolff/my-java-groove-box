package groovebox.ui;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Spinner;

class ChangeVelocityDialog extends Dialog<Integer> {
	private final Spinner<Integer> velocitySpinner;
	ChangeVelocityDialog(int initialValue) {
		setTitle("Velocity");
		velocitySpinner = new Spinner<>(0, 127, initialValue);
		velocitySpinner.setEditable(true);
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		getDialogPane().setContent(velocitySpinner);
		setResultConverter(b -> ButtonType.OK.equals(b) ? velocitySpinner.getValue() : null);
	}

	static void editVelocity(ObjectProperty<Integer> velocityProperty) {
		ChangeVelocityDialog  dialog = new ChangeVelocityDialog(velocityProperty.getValue());
		Optional<Integer> velocityResult = dialog.showAndWait();
		velocityResult.ifPresent(velocityProperty::setValue);
	}
}
