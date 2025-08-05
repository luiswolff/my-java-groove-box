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
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class InstrumentGridPane extends GridPane {
	private final ObjectProperty<EventHandler<ActionEvent>> onAction = new SimpleObjectProperty<>();
	private final Instrument[] instruments = Instrument.values();
	private InstrumentTickBackgroundPane[][] cellTable;

	void defineBeat(FourBarPhrase phrase) {
		clearGrid();
		List<InstrumentTickQuarterNoteGrid> quarterNoteGrids = createSubGrids(phrase);
		for (int row = 0; row < instruments.length; row++) {
			getRowConstraints().add(new RowConstraints(30.0, 30.0, 30.0, Priority.SOMETIMES, VPos.CENTER, true));

			Instrument instrument = instruments[row];
			addLabelColumnCell(row, instrument);

			List<InstrumentTickCellNodes> childrenGrid = createTickColumns(quarterNoteGrids, instrument, getPosition(row));
			for (int col = 0; col < childrenGrid.size(); col++) {
				InstrumentTickCellNodes node = childrenGrid.get(col);
				addListeners(node.foreground());
				addTickColumnCell(row, col + 1, node.foreground(), node.background());
				indexBackground(node.background(), row, col, childrenGrid.size());
			}
		}
	}

	private void indexBackground(InstrumentTickBackgroundPane background, int row, int col, int size) {
		if (cellTable[row] == null) {
			cellTable[row] = new InstrumentTickBackgroundPane[size];
		}
		cellTable[row][col] = background;
	}

	private InstrumentTickPositionEnum getPosition(int row) {
		if (row == 0) {
			return InstrumentTickPositionEnum.TOP;
		} else if (row == instruments.length - 1) {
			return InstrumentTickPositionEnum.BOTTOM;
		}
		return InstrumentTickPositionEnum.CENTER;
	}

	private void clearGrid() {
		getChildren().clear();
		getColumnConstraints().clear();
		getRowConstraints().clear();
		cellTable =  new InstrumentTickBackgroundPane[instruments.length][];
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

	private void addTickColumnCell(int row, int col, InstrumentTickCheckBox checkBox, InstrumentTickBackgroundPane background) {
		if (row == 0) {
			getColumnConstraints().add(new ColumnConstraints(25.0, 25.0, 25.0, Priority.SOMETIMES, HPos.CENTER, true));
		}
		add(background, col, row);
		add(checkBox, col, row);
	}

	private List<InstrumentTickCellNodes> createTickColumns(List<InstrumentTickQuarterNoteGrid> quarterNoteGrids, Instrument instrument,
			InstrumentTickPositionEnum position) {
		List<InstrumentTickCellNodes> childrenGrid = new LinkedList<>();
		for (InstrumentTickQuarterNoteGrid quarterNoteGrid : quarterNoteGrids) {
			childrenGrid.addAll(quarterNoteGrid.createRowCells(instrument, position));
		}
		return new ArrayList<>(childrenGrid);
	}

	private void addListeners(InstrumentTickCheckBox checkBox) {
		checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> modelChanged());
		checkBox.velocityProperty().addListener((obs, oldVal, newVal) -> modelChanged());
	}

	private void modelChanged() {
		onAction.get().handle(new ActionEvent());
	}

	public void highlightColumn(int col) {
		for (InstrumentTickBackgroundPane[] instrumentTickBackgroundPanes : cellTable) {
			for (int j = 0; j < instrumentTickBackgroundPanes.length; j++) {
				if (j == col) {
					instrumentTickBackgroundPanes[j].highlight();
				} else {
					instrumentTickBackgroundPanes[j].downplay();
				}
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
