package groovebox.ui;

import groovebox.model.Beat;
import groovebox.services.SoundService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GrooveBoxController {
	@FXML
	private Label welcomeText;

	private final SoundService soundService;

	private final Beat beat = new Beat();

	public GrooveBoxController() {
		soundService = new SoundService();
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

	@FXML
	protected void onInstrumentChanged(ActionEvent actionEvent) {
		InstrumentTickCheckBox node = (InstrumentTickCheckBox) actionEvent.getSource();
		node.applyInstrumentChange(beat);
		soundService.defineTrack(beat.createTrackData());
	}

	@FXML
	protected void onTempoChanged(ActionEvent actionEvent) {
		TempoSpinner spinner = (TempoSpinner) actionEvent.getSource();
		spinner.applyChange(beat);
		soundService.defineTrack(beat.createTrackData());
	}

	public void close() {
		soundService.close();
	}
}
