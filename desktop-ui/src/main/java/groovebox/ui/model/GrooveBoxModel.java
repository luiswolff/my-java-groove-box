package groovebox.ui.model;

import java.util.List;

import groovebox.service.BeatFactory;
import groovebox.service.BeatSamples;
import groovebox.service.SoundService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;

public class GrooveBoxModel {

	private final PlayedBeatModel playedBeatModel;
	private final SampleBeatModel sampleBeatModel;
	private final SoundControlModel soundControlModel;
	private final ShownPhraseModel shownPhraseModel;

	public GrooveBoxModel(SoundService soundService) {
		this.playedBeatModel = new PlayedBeatModel(soundService);
		this.sampleBeatModel = new SampleBeatModel();
		this.soundControlModel = new SoundControlModel(soundService.getSoundControl());
		this.shownPhraseModel = new ShownPhraseModel(playedBeatModel.phraseProperty());

		init();
	}

	private void init() {
		playedBeatModel.beatProperty().subscribe(soundControlModel::reload);
		playedBeatModel.bind(soundControlModel.tickPositionProperty());
	}

	public BooleanProperty trackIsPlayingProperty() {
		return soundControlModel.runningProperty();
	}

	public IntegerProperty pageCountProperty() {
		return playedBeatModel.phrasesSizeProperty();
	}

	public IntegerProperty phraseIndexProperty() {
		return playedBeatModel.phraseIndexProperty();
	}

	public IntegerProperty highlightedTickProperty() {
		return playedBeatModel.highlightedTickProperty();
	}

	public ObjectProperty<Integer> tempoProperty() {
		return soundControlModel.tempoProperty();
	}

	public BooleanProperty infinityProperty() {
		return soundControlModel.infinityProperty();
	}

	public ObjectProperty<Integer> loopCountProperty() {
		return soundControlModel.loopCountProperty();
	}

	public ListProperty<BeatSamples> sampleBeatFactoriesProperty() {
		return sampleBeatModel.sampleBeatFactoriesProperty();
	}

	public MapProperty<ShownPhraseRow, List<ShownInstrumentTickModel>> phraseGridCellsProperty() {
		return shownPhraseModel.phraseGridCellsProperty();
	}

	public void setBeat(BeatFactory beatFactory) {
		playedBeatModel.beatProperty().set(beatFactory.createBeat());
	}

	public void addNewPhrase() {
		playedBeatModel.addNewPhrase();
	}

	public void removeCurrentPhrase() {
		playedBeatModel.removeCurrentPhrase();
	}

	public SoundControlModel getSoundControlModel() {
		return soundControlModel;
	}

}
