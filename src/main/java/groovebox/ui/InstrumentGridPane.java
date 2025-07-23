package groovebox.ui;


import groovebox.model.Beat;
import groovebox.model.Instrument;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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
				if (j % beat.getResolution() == 1) {
					Region region = new Region();
					region.setStyle("-fx-background-color: blue;");
					add(region, j, i);
				}
				add(new InstrumentTickCheckBox(beat, grooveBoxController), j, i);
			}
		}
	}
}
