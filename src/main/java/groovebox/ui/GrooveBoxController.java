package groovebox.ui;

import groovebox.model.Beat;
import groovebox.services.SoundService;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class GrooveBoxController {

	@FXML
	private InstrumentGridPane instrumentGridPane;

	@FXML
	private TempoSpinner tempoSpinner;

	@FXML
	private LoopCountSpinner loopCountSpinner;

	@FXML
	private CheckBox infinityLoopCheckBox;

	@FXML
	private Button startStopButton;

	private final AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			instrumentGridPane.highlightColumn((int) (soundService.getTickPosition() + 1));
		}
	};

	private final SoundService soundService;

	private final Beat beat = new Beat();

	public GrooveBoxController() {
		soundService = new SoundService(() -> Platform.runLater(() -> {
			startStopButton.setGraphic(Icons.play());
			timer.stop();
			instrumentGridPane.highlightColumn(-1);
		}));
		soundService.defineTrack(beat.createTrackData());
	}

	@FXML
	public void initialize() {
		defineModel();

		startStopButton.setGraphic(Icons.play());
	}

	void defineModel() {
		infinityLoopCheckBox.setSelected(beat.isInfinityLoopCount());

		instrumentGridPane.defineBeat(beat, this);
		loopCountSpinner.defineBeat(beat, this);
		tempoSpinner.defineBeat(beat, this);
	}

	void handleModelChanged() {
		soundService.defineTrack(beat.createTrackData());
	}

	@FXML
	protected void onHelloButtonClick() {
		if (soundService.isRunning()) {
			soundService.stop();
			timer.stop();
			instrumentGridPane.highlightColumn(-1);
			startStopButton.setGraphic(Icons.play());
		} else {
			soundService.start();
			timer.start();
			startStopButton.setGraphic(Icons.stop());
		}
	}

	public void close() {
		soundService.close();
		timer.stop();
		instrumentGridPane.highlightColumn(-1);
	}
}
