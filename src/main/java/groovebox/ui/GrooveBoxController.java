package groovebox.ui;

import java.util.List;

import groovebox.model.Beat;
import groovebox.model.FourBarPhrase;
import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import groovebox.model.Tick;
import groovebox.services.SoundService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GrooveBoxController {
	@FXML
	private Label welcomeText;

	private final SoundService soundService;

	public GrooveBoxController() {
		try {
			soundService = new SoundService();
			QuarterNote note1 = new QuarterNote(new Tick(Instrument.ACOUSTIC_BASS_DRUM, 120),
					null,
					null,
					new Tick(Instrument.ELECTRIC_SNARE, 100));
			QuarterNote note2 = new QuarterNote(new Tick(Instrument.ACOUSTIC_BASS_DRUM, 120),
					null,
					new Tick(Instrument.ELECTRIC_SNARE, 100),
					null);
			soundService.play(new Beat(List.of(new FourBarPhrase(
					note1,
					note2,
					note1,
					note2
			))));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	protected void onHelloButtonClick() {
		if (soundService.isRunning()) {
			soundService.stop();
			welcomeText.setText("Music stoped");
		} else {
			try {
				soundService.start();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			welcomeText.setText("Music started");
		}
	}
}
