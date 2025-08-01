package groovebox.ui;

import java.util.Optional;

import groovebox.model.Beat;
import groovebox.model.FourBarPhrase;
import groovebox.model.Instrument;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InstrumentGridPane extends GridPane {
	// TODO: method is refactored. It is to big now.
	void defineBeat(Beat beat, GrooveBoxController grooveBoxController) {
		getChildren().clear();
		getColumnConstraints().clear();
		getRowConstraints().clear();

		getColumnConstraints().add(new ColumnConstraints(175.0, 175.0, 175.0, Priority.SOMETIMES, HPos.LEFT, true));
		for (int i = 0; i < 4 * beat.getResolution(); i++) {
			getColumnConstraints().add(new ColumnConstraints(25.0, 25.0, 25.0, Priority.SOMETIMES, HPos.CENTER, true));
		}
		for (int i = 0; i < Instrument.values().length; i++) {
			getRowConstraints().add(new RowConstraints(30.0, 30.0 ,30.0, Priority.SOMETIMES, VPos.CENTER, true));
		}

		FourBarPhrase phrase = beat.getPhrases().get(0);
		for (int row = 0; row < Instrument.values().length; row++) {
			Instrument instrument = Instrument.values()[row];
			add(new Label(instrument.name()), 0, row);
			for (int col = 1; col <= 4 * beat.getResolution(); col++) {
				int noteIndex = (col - 1) / 4;
				int tickIndex = (col - 1) % 4;

				Rectangle rectangle = new Rectangle();
				rectangle.setWidth(100.0 * 25.0 / 127.0);
				rectangle.setHeight(5);
				rectangle.setFill(Color.YELLOW);
				StackPane region = new StackPane(rectangle);
				region.setAlignment(Pos.BOTTOM_LEFT);
				if (col % beat.getResolution() == 1) {
					region.setStyle("-fx-background-color: blue");
				}
				InstrumentTickCheckBox checkBox = new InstrumentTickCheckBox(Instrument.values()[row], phrase.getQuarterNote(noteIndex), tickIndex);
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
						rectangle.setWidth(velocity * 25.0 / 127.0);
						checkBox.velocity.setValue(velocity);
					});

				});
				contextMenu.getItems().add(velocityMenuItem);
				checkBox.setContextMenu(contextMenu);
				add(region, col, row);
				add(checkBox, col, row);
			}
		}
	}

	public void highlightColumn(int l) {
		for (Node node : getChildren()) {
			if (!(node instanceof InstrumentTickCheckBox) && GridPane.getColumnIndex(node) == l) {
				String borderStyle = "-fx-border-color: red; " + switch (GridPane.getRowIndex(node)) {
					case 0 -> "-fx-border-width: 2px 2px 0 2px;";
					case 46 -> "-fx-border-width: 0 2px 2px 2px;";
					default -> "-fx-border-width: 0 2px 0 2px;";
				};
				String backgroundStyle = l % 4 == 1 ? "-fx-background-color: blue; " : "";
				node.setStyle(backgroundStyle + borderStyle);
			} else if (!(node instanceof InstrumentTickCheckBox)) {
				String backgroundStyle = GridPane.getColumnIndex(node) % 4 == 1 ? "-fx-background-color: blue; " : "";
				node.setStyle(backgroundStyle);
			}
		}
	}
}
