package groovebox.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import javafx.scene.Node;

class InstrumentTickQuarterNoteGrid {
	private final List<InstrumentTickColumn> tickColumns;

	InstrumentTickQuarterNoteGrid(QuarterNote note) {
		List<InstrumentTickColumn> tickColumns = new ArrayList<>(note.getTicks().size());
		for (int i = 0; i < note.getTicks().size(); i++) {
			tickColumns.add(new InstrumentTickColumn(note.getTicks().get(i), i == 0));
		}
		this.tickColumns = Collections.unmodifiableList(tickColumns);
	}

	List<List<Node>> createRowCells(Instrument instrument, GrooveBoxController grooveBoxController) {
		List<List<Node>> rowCells = new ArrayList<>(tickColumns.size());
		for (InstrumentTickColumn tickColumn : tickColumns) {
			rowCells.add(tickColumn.createCellNotes(instrument, grooveBoxController));
		}
		return rowCells;
	}
}
