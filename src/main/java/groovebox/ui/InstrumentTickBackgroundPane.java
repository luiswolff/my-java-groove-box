package groovebox.ui;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InstrumentTickBackgroundPane extends StackPane {
	private final Rectangle velocityBar = new Rectangle() {{
		setHeight(5);
		setFill(Color.YELLOW);
	}};

	public InstrumentTickBackgroundPane(boolean leading, IntegerProperty velocityProperty) {
		getChildren().add(velocityBar);
		setVelocityBarWidth(velocityProperty.get());
		velocityProperty.addListener((obs, oldVal, newVal) -> setVelocityBarWidth(newVal.intValue()));
		setAlignment(Pos.BOTTOM_LEFT);
		if (leading) {
			setStyle("-fx-background-color: blue;");
		}
	}

	private void setVelocityBarWidth(int width) {
		velocityBar.setWidth((double) width * 25 / 127);
	}
}
