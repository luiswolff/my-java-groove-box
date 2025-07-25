package groovebox.ui;

import groovebox.model.Beat;
import groovebox.services.SoundService;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

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
			System.out.println(soundService.getTickPosition());
		}
	};

	private final SoundService soundService;

	private final Beat beat = new Beat();

	public GrooveBoxController() {
		soundService = new SoundService(() -> Platform.runLater(() -> {
			SVGPath playIcon = new SVGPath();
			playIcon.setContent("M0,0 L10,5 L0,10 Z"); // Einfaches Dreieck
			playIcon.setFill(Color.BLACK);
			startStopButton.setGraphic(playIcon);
			timer.stop();
		}));
		soundService.defineTrack(beat.createTrackData());
	}

	@FXML
	public void initialize() {
		defineModel();

		SVGPath playIcon = new SVGPath();
		playIcon.setContent("M0,0 L10,5 L0,10 Z"); // Einfaches Dreieck
		playIcon.setFill(Color.BLACK);
		startStopButton.setGraphic(playIcon);
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

			SVGPath playIcon = new SVGPath();
			playIcon.setContent("M0,0 L10,5 L0,10 Z"); // Einfaches Dreieck
			playIcon.setFill(Color.BLACK);
			startStopButton.setGraphic(playIcon);
		} else {
			soundService.start();
			timer.start();

			Rectangle stopIcon = new Rectangle();
			stopIcon.setWidth(10);
			stopIcon.setHeight(10);
			stopIcon.setFill(Color.RED); // Klassische Stop-Farbe
			startStopButton.setGraphic(stopIcon);
		}
	}

	public void close() {
		soundService.close();
		timer.stop();
	}
}
