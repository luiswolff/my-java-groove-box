package groovebox.ui;

import java.util.LinkedList;
import java.util.List;

import groovebox.model.Instrument;
import groovebox.model.Tick;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

class InstrumentTickColumn {
	private final List<Tick> ticks;
	private final boolean leadingColumn;
	List<ObjectProperty<EventHandler<ActionEvent>>> onActions = new LinkedList<>();

	InstrumentTickColumn(List<Tick> ticks, boolean leadingColumn) {
		this.ticks = ticks;
		this.leadingColumn = leadingColumn;
	}

	List<Node> createCellNotes(Instrument instrument, Runnable onModelChanged) {
		InstrumentTickCheckBox checkBox = new InstrumentTickCheckBox(instrument, ticks);
		InstrumentTickBackgroundPane backgroundPane = new InstrumentTickBackgroundPane(leadingColumn, checkBox.velocityProperty());

		onActions.add(checkBox.onActionProperty());
		checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> onModelChanged.run());
		checkBox.velocityProperty().addListener((obs, oldVal, newVal) -> onModelChanged.run());

		return List.of(backgroundPane, checkBox);
	}

}
