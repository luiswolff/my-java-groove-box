package groovebox.ui;

import java.util.function.Supplier;

import groovebox.service.Beat;
import groovebox.service.SoundService;
import javafx.animation.AnimationTimer;
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
			Integer tickPosition = soundService.getTickPosition();
			if (tickPosition != null) {
				grooveBoxModel.setHighlightedTick(tickPosition);
			} else {
				timer.stop();
				grooveBoxModel.trackIsPaused();
			}
		}
	};

	private final SoundService soundService = new SoundService();

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
		soundService.defineTrack(beat);
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
		Beat beat = getBeat(source);
		grooveBoxModel.setBeat(beat);
		handleModelChanged();
	}

	private static Beat getBeat(Object source) {
		Supplier<Beat> beatSupplier;
		if (source instanceof SampleBeatMenu) {
			//noinspection unchecked
			beatSupplier = (Supplier<Beat>) ((SampleBeatMenu) source).getUserData();
		} else {
			//noinspection unchecked
			beatSupplier = (Supplier<Beat>) ((SampleBeatMenu.SampleBeatMenuItem) source).getUserData();
		}
		return beatSupplier.get();
	}

	public void close() {
		soundService.close();
		timer.stop();
	}
}
