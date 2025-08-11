package groovebox.ui;

import java.util.List;
import java.util.function.Supplier;

import groovebox.model.Beat;
import groovebox.model.SampleBeatFactory;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class SampleBeatMenu extends SplitMenuButton {
	private final ListProperty<SampleBeatFactory> sampleBeatFactories = new SimpleListProperty<>();

	public SampleBeatMenu() {
		setUserData((Supplier<Beat>) Beat::new);
		sampleBeatFactories.addListener(this::handleSampleBeatChanged);
	}

	private void handleSampleBeatChanged(ListChangeListener.Change<? extends SampleBeatFactory> change) {
		while (change.next()) {
			if (change.wasAdded()) {
				addSamples(change.getAddedSubList());
			}
			if (change.wasRemoved()) {
				removeSamples(change.getRemoved());
			}
		}
	}

	void apply(GrooveBoxModel model) {
		sampleBeatFactories.bindBidirectional(model.sampleBeatFactoriesProperty());
	}

	private void addSamples(List<? extends SampleBeatFactory> sampleBeats) {
		sampleBeats.stream().map(SampleBeatMenuItem::new).forEach(getItems()::add);
	}

	private void removeSamples(List<? extends SampleBeatFactory> sampleBeats) {
		sampleBeats.forEach(sampleBeatFactory -> getItems().removeIf(
				menuItem -> ((SampleBeatMenuItem) menuItem).sampleBeatFactory.equals(sampleBeatFactory)));
	}

	class SampleBeatMenuItem extends MenuItem {
		private final SampleBeatFactory sampleBeatFactory;

		public SampleBeatMenuItem(SampleBeatFactory sampleBeatFactory) {
			super(sampleBeatFactory.name());
			setUserData((Supplier<Beat>) sampleBeatFactory::createBeat);
			setOnAction(SampleBeatMenu.this.getOnAction());
			this.sampleBeatFactory = sampleBeatFactory;
		}
	}
}
