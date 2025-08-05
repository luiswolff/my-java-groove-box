package groovebox.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

class InstrumentTickQuarterNoteGrid {
	private final List<InstrumentTickColumn> tickColumns;
	List<ObjectProperty<EventHandler<ActionEvent>>> onActions = new LinkedList<>();

	InstrumentTickQuarterNoteGrid(QuarterNote note) {
		List<InstrumentTickColumn> tickColumns = new ArrayList<>(note.getTicks().size());
		for (int i = 0; i < note.getTicks().size(); i++) {
			tickColumns.add(new InstrumentTickColumn(note.getTicks().get(i), i == 0));
		}
		this.tickColumns = Collections.unmodifiableList(tickColumns);
	}

	List<InstrumentTickCellNodes> createRowCells(Instrument instrument, InstrumentTickPositionEnum position) {
		List<InstrumentTickCellNodes> rowCells = new ArrayList<>(tickColumns.size());
		for (InstrumentTickColumn tickColumn : tickColumns) {
			rowCells.add(tickColumn.createCellNotes(instrument, position));
			onActions.addAll(tickColumn.onActions);
		}
		return rowCells;
	}
}
