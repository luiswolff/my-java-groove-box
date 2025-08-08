package groovebox.ui;

import groovebox.model.Beat;
import groovebox.model.SampleBeatFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class SampleBeatMenu extends SplitMenuButton {
	private final ObjectProperty<Beat> beatProperty = new SimpleObjectProperty<>(new Beat());
	ObjectProperty<Beat> beatProperty() {
		return this.beatProperty;
	}
	void defineSamples(SampleBeatFactory[] sampleBeats) {
		for (SampleBeatFactory value : sampleBeats) {
			MenuItem menuItem = new MenuItem(value.name());
			menuItem.setUserData(value);
			menuItem.setOnAction(event -> beatProperty.set(value.createBeat()));
			getItems().add(menuItem);
		}
		setOnAction(event -> beatProperty.set(new Beat()));
	}
}
