package groovebox.ui;

import java.util.List;

import groovebox.service.Instrument;
import groovebox.ui.model.GrooveBoxModel;
import groovebox.ui.model.ShownInstrumentTickModel;
import groovebox.ui.model.ShownPhraseRow;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableMap;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class InstrumentGridPane extends GridPane {
	private final IntegerProperty highlightedTick =  new SimpleIntegerProperty();

	public InstrumentGridPane() {
		highlightedTickProperty().addListener((observable, oldValue, newValue) -> highlightTick());
	}

	IntegerProperty highlightedTickProperty() {
		return highlightedTick;
	}

	void apply(GrooveBoxModel model) {
		highlightedTickProperty().bind(model.highlightedTickProperty());
		model.phraseGridCellsProperty().subscribe(this::onPhraseGridCellsChanged);
	}

	private final Instrument[] instruments = Instrument.values();

	private InstrumentTickBackgroundPane[][] cellTable;

	private void onPhraseGridCellsChanged(ObservableMap<ShownPhraseRow, List<ShownInstrumentTickModel>> phraseGridCells) {
		clearGrid();
		addColumnConstrains(phraseGridCells.values().stream().mapToInt(List::size).max().orElse(0));
		phraseGridCells.forEach((row, shownInstrumentTicks) -> {
			addRowConstrainsAndLabel(row);

			for (int col = 0; col < shownInstrumentTicks.size(); col++) {
				ShownInstrumentTickModel tick = shownInstrumentTicks.get(col);
				InstrumentTickCheckBox checkBox = new InstrumentTickCheckBox(tick);
				InstrumentTickBackgroundPane backgroundPane = new InstrumentTickBackgroundPane(tick.isLeadColumn(), checkBox.velocityProperty(), tick.getPosition());

				InstrumentTickCellNodes node = new InstrumentTickCellNodes(checkBox, backgroundPane);
				add(node.background(), col + 1, row.rowIndex());
				add(node.foreground(), col + 1, row.rowIndex());
				indexBackground(node.background(), row.rowIndex(), col, shownInstrumentTicks.size());
			}
		});
	}

	private void addColumnConstrains(int columnCount) {
		getColumnConstraints().add(new ColumnConstraints(175.0, 175.0, 175.0, Priority.SOMETIMES, HPos.LEFT, true));
		for (int i = 0; i < columnCount; i++) {
			getColumnConstraints().add(new ColumnConstraints(25.0, 25.0, 25.0, Priority.SOMETIMES, HPos.CENTER, true));
		}
	}

	private void addRowConstrainsAndLabel(ShownPhraseRow row) {
		getRowConstraints().add(new RowConstraints(30.0, 30.0, 30.0, Priority.SOMETIMES, VPos.CENTER, true));
		add(new Label(row.label()), 0, row.rowIndex());
	}

	private void indexBackground(InstrumentTickBackgroundPane background, int row, int col, int size) {
		if (cellTable[row] == null) {
			cellTable[row] = new InstrumentTickBackgroundPane[size];
		}
		cellTable[row][col] = background;
	}

	private void clearGrid() {
		getChildren().clear();
		getColumnConstraints().clear();
		getRowConstraints().clear();
		cellTable =  new InstrumentTickBackgroundPane[instruments.length][];
	}

	private void highlightTick() {
		if (cellTable != null) {
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
}
