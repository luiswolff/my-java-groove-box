package groovebox.ui;

import java.util.function.Supplier;

import groovebox.model.Beat;
import groovebox.services.SoundService;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
		instrumentGridPane.apply(grooveBoxModel, this::handleModelChanged);
		loopCountSpinner.apply(grooveBoxModel, this::handleModelChanged);
		tempoSpinner.apply(grooveBoxModel, this::handleModelChanged);
		infinityLoopCheckBox.apply(grooveBoxModel);
		sampleBeatMenu.apply(grooveBoxModel);
		playStopButton.apply(grooveBoxModel);
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

	@FXML
	protected void onSampleBeatChanged(ActionEvent event) {
		Object source = event.getSource();
		Supplier<Beat> beatSupplier;
		if (source instanceof SampleBeatMenu) {
			//noinspection unchecked
			beatSupplier = (Supplier<Beat>) ((SampleBeatMenu) source).getUserData();
		} else {
			//noinspection unchecked
			beatSupplier = (Supplier<Beat>) ((SampleBeatMenu.SampleBeatMenuItem)source).getUserData();
		}
		grooveBoxModel.setBeat(beatSupplier.get());
		handleModelChanged();
	}

	public void close() {
		soundService.close();
		timer.stop();
		instrumentGridPane.highlightColumn(-1);
	}
}
