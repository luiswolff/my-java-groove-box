package groovebox.ui;

import groovebox.model.Beat;
import groovebox.model.SampleBeatFactory;
import groovebox.services.SoundService;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

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
	private SampleBeatMenu sampleBeats;

	private final GrooveBoxModel grooveBoxModel = new GrooveBoxModel();

	private final AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			instrumentGridPane.highlightColumn((int) (soundService.getTickPosition()));
		}
	};

	private final SoundService soundService;

	public GrooveBoxController() {
		soundService = new SoundService(() -> Platform.runLater(() -> {
			grooveBoxModel.trackIsPaused();
			timer.stop();
			instrumentGridPane.highlightColumn(-1);
		}));
	}

	@FXML
	public void initialize() {
		instrumentGridPane.setChangedCallback(this::handleModelChanged);
		loopCountSpinner.setChangeCallback(this::handleModelChanged);
		tempoSpinner.setChangeCallback(this::handleModelChanged);

		playStopButton.apply(grooveBoxModel);
		infinityLoopCheckBox.apply(grooveBoxModel);

		instrumentGridPane.phraseProperty().bind(grooveBoxModel.phraseProperty());
		loopCountSpinner.beatProperty().bind(grooveBoxModel.beatProperty());
		tempoSpinner.beatProperty().bind(grooveBoxModel.beatProperty());

		grooveBoxModel.beatProperty().bind(sampleBeats.beatProperty());
		grooveBoxModel.beatProperty().addListener((observable, oldValue, newValue) -> handleModelChanged());

		sampleBeats.defineSamples(SampleBeatFactory.values());
	}

	private void handleModelChanged() {
		Beat beat = grooveBoxModel.beatProperty().get();
		soundService.defineTrack(beat.createTrackData());
	}

	@FXML
	protected void onStartStopButtonClicked() {
		if (soundService.isRunning()) {
			soundService.stop();
			timer.stop();
			instrumentGridPane.highlightColumn(-1);
			grooveBoxModel.trackIsPaused();
		} else {
			soundService.start();
			timer.start();
			grooveBoxModel.trackIsPlaying();
		}
	}

	public void close() {
		soundService.close();
		timer.stop();
		instrumentGridPane.highlightColumn(-1);
	}
}
