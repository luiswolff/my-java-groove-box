package groovebox.ui;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class InstrumentTickBackgroundPane extends StackPane {
	private final Rectangle velocityBar = new Rectangle() {{
		setHeight(5);
		setFill(Color.YELLOW);
	}};
	private final boolean leading;
	private final InstrumentTickPositionEnum position;
	private boolean highlighted = false;

	InstrumentTickBackgroundPane(boolean leading, IntegerProperty velocityProperty, InstrumentTickPositionEnum position) {
		this.leading = leading;
		this.position = position;

		getChildren().add(velocityBar);
		setVelocityBarWidth(velocityProperty.get());
		velocityProperty.addListener((obs, oldVal, newVal) -> setVelocityBarWidth(newVal.intValue()));
		setAlignment(Pos.BOTTOM_LEFT);
		defineStyle();
	}

	private void setVelocityBarWidth(int width) {
		velocityBar.setWidth((double) width * 25 / 127);
	}

	void highlight() {
		if (!highlighted) {
			highlighted = true;
			defineStyle();
		}
	}

	void downplay() {
		if (highlighted) {
			highlighted = false;
			defineStyle();
		}
	}

	void defineStyle() {
		StringBuilder styleBuilder = new StringBuilder();
		if (highlighted) {
			styleBuilder.append("-fx-border-color: red;");
			styleBuilder.append(position.getBorderStyle());
		}
		if (leading) {
			styleBuilder.append("-fx-background-color: blue;");
		}
		setStyle(styleBuilder.toString());
	}
}
