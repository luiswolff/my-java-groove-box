package groovebox.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import groovebox.model.FourBarPhrase;
import groovebox.model.Instrument;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class InstrumentGridPane extends GridPane {
	private final ObjectProperty<EventHandler<ActionEvent>> onAction = new SimpleObjectProperty<>();

	void defineBeat(FourBarPhrase phrase) {
		clearGrid();
		List<InstrumentTickQuarterNoteGrid> quarterNoteGrids = createSubGrids(phrase);
		for (int row = 0; row < Instrument.values().length; row++) {
			getRowConstraints().add(new RowConstraints(30.0, 30.0, 30.0, Priority.SOMETIMES, VPos.CENTER, true));

			Instrument instrument = Instrument.values()[row];
			addLabelColumnCell(row, instrument);

			List<List<Node>> childrenGrid = createTickColumns(quarterNoteGrids, instrument);
			for (int i = 0; i < childrenGrid.size(); i++) {
				addTickColumnCell(row, childrenGrid, i);
			}
		}
	}

	private void clearGrid() {
		getChildren().clear();
		getColumnConstraints().clear();
		getRowConstraints().clear();
	}

	private static List<InstrumentTickQuarterNoteGrid> createSubGrids(FourBarPhrase phrase) {
		return phrase.getQuarterNotes().stream()
				.map(InstrumentTickQuarterNoteGrid::new)
				.toList();
	}

	private void addLabelColumnCell(int row, Instrument instrument) {
		if (row == 0) {
			getColumnConstraints().add(new ColumnConstraints(175.0, 175.0, 175.0, Priority.SOMETIMES, HPos.LEFT, true));
		}
		add(new Label(instrument.name()), 0, row);
	}

	private void addTickColumnCell(int row, List<List<Node>> childrenGrid, int i) {
		if (row == 0) {
			getColumnConstraints().add(new ColumnConstraints(25.0, 25.0, 25.0, Priority.SOMETIMES, HPos.CENTER, true));
		}
		List<Node> children = childrenGrid.get(i);
		for (Node child : children) {
			add(child, i + 1, row);
		}
	}

	private List<List<Node>> createTickColumns(List<InstrumentTickQuarterNoteGrid> quarterNoteGrids, Instrument instrument) {
		List<List<Node>> childrenGrid = new LinkedList<>();
		for (InstrumentTickQuarterNoteGrid quarterNoteGrid : quarterNoteGrids) {
			childrenGrid.addAll(quarterNoteGrid.createRowCells(instrument, this::modelChanged));
		}
		return new ArrayList<>(childrenGrid);
	}

	private void modelChanged() {
		onAction.get().handle(new ActionEvent());
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

	public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
		return onAction;
	}

	@SuppressWarnings("unused") // used by FXML
	public final void setOnAction(EventHandler<ActionEvent> value) {
		onActionProperty().set(value);
	}

	@SuppressWarnings("unused") // used by FXML
	public final EventHandler<ActionEvent> getOnAction() {
		return onActionProperty().get();
	}
}
