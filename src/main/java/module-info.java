module groovebox {
	requires java.desktop;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;

	opens groovebox.ui to javafx.fxml;
	exports groovebox.ui;
}