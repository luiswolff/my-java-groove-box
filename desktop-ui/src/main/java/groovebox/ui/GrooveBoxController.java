package groovebox.ui;

import java.util.function.Supplier;

import groovebox.service.BeatFactory;
import groovebox.service.Beat;
import groovebox.service.Phrase;
import groovebox.service.SoundService;
import groovebox.service.TickPosition;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GrooveBoxController {

	@FXML
	private InstrumentGridPane instrumentGridPane;

	@FXML
	private TempoSpinner tempoSpinner;

	@FXML
	private LoopCountSpinner loopCountSpinner;

	@FXML
	private InfinityLoopCheckBox infinityLoopCheckBox;

	@FXML
	private PlayStopButton playStopButton;

	@FXML
	private SampleBeatMenu sampleBeatMenu;

	@FXML
	private PhrasePagination phrasePagination;


	private final AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			TickPosition tickPosition = soundService.getSoundControl().getTickPosition();
			if (tickPosition != null) {
				grooveBoxModel.setHighlightedTick(tickPosition);
			} else {
				timer.stop();
				grooveBoxModel.trackIsPaused();
			}
		}
	};

	private final SoundService soundService = new SoundService();
	private final GrooveBoxModel grooveBoxModel = new GrooveBoxModel(soundService.getSoundControl());

	@FXML
	public void initialize() {
		instrumentGridPane.apply(grooveBoxModel);
		loopCountSpinner.apply(grooveBoxModel);
		tempoSpinner.apply(grooveBoxModel);
		infinityLoopCheckBox.apply(grooveBoxModel);
		sampleBeatMenu.apply(grooveBoxModel);
		playStopButton.apply(grooveBoxModel);
		phrasePagination.apply(grooveBoxModel);

		soundService.setBeat(grooveBoxModel.beatProperty().get());
	}

	@FXML
	protected void onStartStopButtonClicked() {
		if (soundService.isRunning()) {
			soundService.stop();
			timer.stop();
			grooveBoxModel.trackIsPaused();
		} else {
			soundService.start();
			timer.start();
			grooveBoxModel.trackIsPlaying();
		}
	}

	@FXML
	protected void onSampleBeatChanged(ActionEvent event) {
		Object source = event.getSource();
		Beat<ObservableList<Phrase>> beat = getBeat(source);
		grooveBoxModel.setBeat(beat);
	}

	private Beat<ObservableList<Phrase>> getBeat(Object source) {
		Supplier<BeatFactory> beatSupplier;
		if (source instanceof SampleBeatMenu) {
			//noinspection unchecked
			beatSupplier = (Supplier<BeatFactory>) ((SampleBeatMenu) source).getUserData();
		} else {
			//noinspection unchecked
			beatSupplier = (Supplier<BeatFactory>) ((SampleBeatMenu.SampleBeatMenuItem) source).getUserData();
		}
		BeatFactory beatFactory = beatSupplier.get();
		Beat<ObservableList<Phrase>> beat = beatFactory.createBeat(FXCollections::observableArrayList);
		soundService.setBeat(beat);
		beatFactory.apply(soundService.getSoundControl());
		return beat;
	}

	@FXML
	protected void onAddPhraseButtonClicked() {
		grooveBoxModel.addNewPhrase();
	}

	@FXML
	protected void onRemovePhraseButtonClicked() {
		grooveBoxModel.removeCurrentPhrase();
	}

	public void close() {
		soundService.close();
		timer.stop();
	}
}
