module groove.box.ui {
	requires groove.box.core;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;

	opens groovebox.ui to javafx.fxml;
	exports groovebox.ui;
}