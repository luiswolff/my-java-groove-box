package groovebox.ui.model;

import java.util.Arrays;

import groovebox.service.BeatSamples;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class SampleBeatModel {

	private final ListProperty<BeatSamples> sampleBeatFactories = new SimpleListProperty<>(FXCollections.observableArrayList());

	public SampleBeatModel() {
		Arrays.stream(BeatSamples.values()).forEach(this::addSampleBeatFactory);
	}

	public ListProperty<BeatSamples> sampleBeatFactoriesProperty() {
		return sampleBeatFactories;
	}

	public void addSampleBeatFactory(BeatSamples sampleBeatFactory) {
		sampleBeatFactories.add(sampleBeatFactory);
	}
}
