package groovebox.ui;


import groovebox.model.Beat;
import groovebox.model.Instrument;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class InstrumentGridPane extends GridPane {
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

		for (int i = 0; i < Instrument.values().length; i++) {
			Instrument instrument = Instrument.values()[i];
			add(new Label(instrument.name()), 0, i);
			for (int j = 1; j <= 4 * beat.getResolution(); j++) {
				Region region = new Region();
				if (j % beat.getResolution() == 1) {
					region.setStyle("-fx-background-color: blue");
				}
				add(region, j, i);
				add(new InstrumentTickCheckBox(beat, grooveBoxController, j - 1, i), j, i);
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
