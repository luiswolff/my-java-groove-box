package groovebox.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import groovebox.model.FourBarPhrase;
import groovebox.model.Instrument;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class InstrumentGridPane extends GridPane {
	private final ObjectProperty<FourBarPhrase> phrase = new SimpleObjectProperty<>();
	private final IntegerProperty highlightedTick =  new SimpleIntegerProperty();

	private Runnable changeCallback = null;

	public InstrumentGridPane() {
		phrase.addListener((observable, oldValue, newValue) -> defineBeat());
		highlightedTickProperty().addListener((observable, oldValue, newValue) -> highlightTick());
	}

	ObjectProperty<FourBarPhrase> phraseProperty() {
		return phrase;
	}

	IntegerProperty highlightedTickProperty() {
		return highlightedTick;
	}

	void apply(GrooveBoxModel model, Runnable changeCallback) {
		phraseProperty().bind(model.phraseProperty());
		highlightedTickProperty().bind(model.highlightedTickProperty());
		this.changeCallback = changeCallback;
	}
	private final Instrument[] instruments = Instrument.values();

	private InstrumentTickBackgroundPane[][] cellTable;

	private void defineBeat() {
		clearGrid();
		List<InstrumentTickQuarterNoteGrid> quarterNoteGrids = createSubGrids();
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

	private List<InstrumentTickQuarterNoteGrid> createSubGrids() {
		return phrase.get().getQuarterNotes().stream()
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
		changeCallback.run();
	}

	private void highlightTick() {
		int col = highlightedTickProperty().get();
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
}
