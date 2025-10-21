package groovebox.ui;

import java.util.List;
import java.util.Map;

import groovebox.service.Instrument;
import groovebox.ui.model.GrooveBoxModel;
import groovebox.ui.model.ShownInstrumentNoteModel;
import groovebox.ui.model.ShownInstrumentRowModel;
import groovebox.ui.model.ShownInstrumentTickModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
		model.getShownPhraseModel().shownPhraseModelProperty().subscribe(this::onShownPhraseModelChanged);
	}

	private final Instrument[] instruments = Instrument.values();

	private InstrumentTickBackgroundPane[][] cellTable;

	private void onShownPhraseModelChanged(Map<Instrument, ShownInstrumentRowModel> phraseMap) {
		clearGrid();
		int row = 0;
		for (Instrument instrument : phraseMap.keySet()) {
			getRowConstraints().add(new RowConstraints(30.0, 30.0, 30.0, Priority.SOMETIMES, VPos.CENTER, true));
			addLabelColumnCell(row, instrument);

			ShownInstrumentRowModel rowModel = phraseMap.get(instrument);
			List<ShownInstrumentNoteModel> shownNotes = rowModel.getShownNotes();
			for (int i = 0; i < shownNotes.size(); i++) {
				ShownInstrumentNoteModel noteModel = shownNotes.get(i);
				List<ShownInstrumentTickModel> shownInstrumentTicks = noteModel.getShownInstrumentTicks();
				for (int j = 0; j < shownInstrumentTicks.size(); j++) {
					int col = i * shownInstrumentTicks.size() + j;
					ShownInstrumentTickModel tick = shownInstrumentTicks.get(j);
					InstrumentTickCheckBox checkBox = new InstrumentTickCheckBox(tick);
					InstrumentTickBackgroundPane backgroundPane = new InstrumentTickBackgroundPane(j == 0, checkBox.velocityProperty(), getPosition(row));

					InstrumentTickCellNodes node = new InstrumentTickCellNodes(checkBox, backgroundPane);
					addTickColumnCell(row, col + 1, node.foreground(), node.background());
					indexBackground(node.background(), row, col, shownNotes.size() * shownInstrumentTicks.size());
				}
			}

			row++;
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
