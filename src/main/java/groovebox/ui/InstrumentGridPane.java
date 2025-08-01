package groovebox.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import groovebox.model.Beat;
import groovebox.model.FourBarPhrase;
import groovebox.model.Instrument;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class InstrumentGridPane extends GridPane {
	private List<QuarterNoteGrid> quarterNoteGrids;
	// TODO: method is refactored. It is to big now.
	void defineBeat(Beat beat, GrooveBoxController grooveBoxController) {
		getChildren().clear();
		getColumnConstraints().clear();
		getRowConstraints().clear();

		getColumnConstraints().add(new ColumnConstraints(175.0, 175.0, 175.0, Priority.SOMETIMES, HPos.LEFT, true));
		for (int i = 0; i < 4 * beat.getResolution(); i++) {
			getColumnConstraints().add(new ColumnConstraints(25.0, 25.0, 25.0, Priority.SOMETIMES, HPos.CENTER, true));
		}
		for (int i = 0; i < Instrument.values().length; i++) {
			getRowConstraints().add(new RowConstraints(30.0, 30.0 ,30.0, Priority.SOMETIMES, VPos.CENTER, true));
		}

		FourBarPhrase phrase = beat.getPhrases().get(0);

		quarterNoteGrids = phrase.getQuarterNotes().stream()
				.map(QuarterNoteGrid::new)
				.collect(Collectors.toList());
		for (int row = 0; row < Instrument.values().length; row++) {
			Instrument instrument = Instrument.values()[row];
			add(new Label(instrument.name()), 0, row);

			List<List<Node>> childrenGrid = new LinkedList<>();
			for (QuarterNoteGrid quarterNoteGrid : quarterNoteGrids) {
				childrenGrid.addAll(quarterNoteGrid.createRowCells(instrument, grooveBoxController));
			}
			for (int i = 0; i < childrenGrid.size(); i++) {
				List<Node> children = childrenGrid.get(i);
				for (Node child : children) {
					add(child, i + 1, row);
				}
			}
		}
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
}
