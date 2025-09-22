package groovebox.ui;

import java.util.List;
import java.util.function.Supplier;

import groovebox.service.BeatFactory;
import groovebox.service.BeatSamples;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

public class SampleBeatMenu extends SplitMenuButton {
	private final ListProperty<BeatSamples> sampleBeatFactories = new SimpleListProperty<>();

	public SampleBeatMenu() {
		setUserData((Supplier<BeatFactory>) BeatFactory.builder()::build);
		sampleBeatFactories.addListener(this::handleSampleBeatChanged);
	}

	private void handleSampleBeatChanged(ListChangeListener.Change<? extends BeatSamples> change) {
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

	private void addSamples(List<? extends BeatSamples> sampleBeats) {
		sampleBeats.stream().map(SampleBeatMenuItem::new).forEach(getItems()::add);
	}

	private void removeSamples(List<? extends BeatSamples> sampleBeats) {
		sampleBeats.forEach(sampleBeatFactory -> getItems().removeIf(
				menuItem -> ((SampleBeatMenuItem) menuItem).sampleBeatFactory.equals(sampleBeatFactory)));
	}

	class SampleBeatMenuItem extends MenuItem {
		private final BeatSamples sampleBeatFactory;

		public SampleBeatMenuItem(BeatSamples sampleBeatFactory) {
			super(sampleBeatFactory.name());
			setUserData((Supplier<BeatFactory>) sampleBeatFactory::createBeat);
			setOnAction(SampleBeatMenu.this.getOnAction());
			this.sampleBeatFactory = sampleBeatFactory;
		}
	}
}
