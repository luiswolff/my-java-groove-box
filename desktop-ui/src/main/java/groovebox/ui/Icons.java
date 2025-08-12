package groovebox.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

class Icons {
	private Icons () {}

	static Shape stop() {
		Rectangle stopIcon = new Rectangle();
		stopIcon.setWidth(10);
		stopIcon.setHeight(10);
		stopIcon.setFill(Color.RED);
		return stopIcon;
	}

	static Shape play() {
		SVGPath playIcon = new SVGPath();
		playIcon.setContent("M0,0 L10,5 L0,10 Z");
		playIcon.setFill(Color.BLACK);
		return playIcon;
	}
}
