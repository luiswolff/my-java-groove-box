package groovebox.ui;

import groovebox.model.Beat;
import groovebox.model.SampleBeatFactory;
import groovebox.services.SoundService;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

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

	@FXML
	private SplitMenuButton sampleBeats;

	private final AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			instrumentGridPane.highlightColumn((int) (soundService.getTickPosition() + 1));
		}
	};

	private final SoundService soundService;

	private Beat beat;

	public GrooveBoxController() {
		soundService = new SoundService(() -> Platform.runLater(() -> {
			startStopButton.setGraphic(Icons.play());
			timer.stop();
			instrumentGridPane.highlightColumn(-1);
		}));
	}

	@FXML
	public void initialize() {
		setModel(new Beat());

		startStopButton.setGraphic(Icons.play());

		for (SampleBeatFactory value : SampleBeatFactory.values()) {
			MenuItem menuItem = new MenuItem(value.name());
			menuItem.setUserData(value);
			menuItem.setOnAction(event -> setModel(value.createBeat()));
			sampleBeats.getItems().add(menuItem);
		}
		sampleBeats.setOnAction(event -> setModel(new Beat()));
	}

	private void setModel(Beat beat) {
		this.beat = beat;
		defineModel();
	}

	private void defineModel() {
		infinityLoopCheckBox.setSelected(beat.isInfinityLoopCount());

		instrumentGridPane.defineBeat(beat, this);
		loopCountSpinner.defineBeat(beat, this);
		tempoSpinner.defineBeat(beat, this);

		soundService.defineTrack(beat.createTrackData());
	}

	void handleModelChanged() {
		soundService.defineTrack(beat.createTrackData());
	}

	@FXML
	protected void onStartStopButtonClicked() {
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
