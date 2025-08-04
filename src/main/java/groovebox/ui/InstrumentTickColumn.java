package groovebox.ui;

import java.util.List;

import groovebox.model.Instrument;
import groovebox.model.Tick;
import javafx.scene.Node;

class InstrumentTickColumn {
	private final List<Tick> ticks;
	private final boolean leadingColumn;

	InstrumentTickColumn(List<Tick> ticks, boolean leadingColumn) {
		this.ticks = ticks;
		this.leadingColumn = leadingColumn;
	}

	List<Node> createCellNotes(Instrument instrument, GrooveBoxController grooveBoxController) {
		InstrumentTickCheckBox checkBox = new InstrumentTickCheckBox(instrument, ticks);
		InstrumentTickBackgroundPane backgroundPane = new InstrumentTickBackgroundPane(leadingColumn, checkBox.velocityProperty());

		checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> grooveBoxController.handleModelChanged());
		checkBox.velocityProperty().addListener((obs, oldVal, newVal) -> grooveBoxController.handleModelChanged());

		return List.of(backgroundPane, checkBox);
	}

}
