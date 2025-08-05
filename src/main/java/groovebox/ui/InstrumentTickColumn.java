package groovebox.ui;

import java.util.LinkedList;
import java.util.List;

import groovebox.model.Instrument;
import groovebox.model.Tick;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

class InstrumentTickColumn {
	private final List<Tick> ticks;
	private final boolean leadingColumn;
	List<ObjectProperty<EventHandler<ActionEvent>>> onActions = new LinkedList<>();

	InstrumentTickColumn(List<Tick> ticks, boolean leadingColumn) {
		this.ticks = ticks;
		this.leadingColumn = leadingColumn;
	}

	InstrumentTickCellNodes createCellNotes(Instrument instrument, InstrumentTickPositionEnum position) {
		InstrumentTickCheckBox checkBox = new InstrumentTickCheckBox(instrument, ticks);
		InstrumentTickBackgroundPane backgroundPane = new InstrumentTickBackgroundPane(leadingColumn, checkBox.velocityProperty(), position);

		onActions.add(checkBox.onActionProperty());

		return new InstrumentTickCellNodes(checkBox, backgroundPane);
	}

}
