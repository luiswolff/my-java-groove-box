package groovebox.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class QuarterNoteGrid {
	private final List<TickColumn> tickColumns;

	QuarterNoteGrid(QuarterNote note) {
		this.tickColumns = note.getTicks().stream()
				.map(TickColumn::new)
				.collect(Collectors.toList());
	}

	List<List<Node>> createRowCells(Instrument instrument, GrooveBoxController grooveBoxController) {
		List<List<Node>> rowCells = new ArrayList<>(tickColumns.size());
		for (int col = 0; col < tickColumns.size(); col++) {
			Rectangle rectangle = new Rectangle();
			rectangle.setWidth(100.0 * 25.0 / 127.0);
			rectangle.setHeight(5);
			rectangle.setFill(Color.YELLOW);
			StackPane region = new StackPane(rectangle);
			region.setAlignment(Pos.BOTTOM_LEFT);
			if (col == 0) {
				region.setStyle("-fx-background-color: blue");
			}

			TickColumn tickColumn = tickColumns.get(col);

			rowCells.add(List.of(region, tickColumn.createCheckBox(instrument, rectangle, grooveBoxController)));
		}
		return rowCells;
	}
}
