package groovebox.ui;

import groovebox.model.Beat;
import groovebox.services.SoundService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GrooveBoxController {
	@FXML
	private Label welcomeText;

	@FXML
	private InstrumentGridPane instrumentGridPane;

	@FXML
	private TempoSpinner tempoSpinner;

	@FXML
	private LoopCountSpinner loopCountSpinner;

	private final SoundService soundService;

	private final Beat beat = new Beat();

	public GrooveBoxController() {
		soundService = new SoundService();
		soundService.defineTrack(beat.createTrackData());
	}

	@FXML
	public void initialize() {
		defineModel();
	}

	void defineModel() {
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
			welcomeText.setText("Music stoped");
		} else {
			soundService.start();
			welcomeText.setText("Music started");
		}
	}

	public void close() {
		soundService.close();
	}
}
