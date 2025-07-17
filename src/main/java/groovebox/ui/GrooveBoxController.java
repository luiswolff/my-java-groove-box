package groovebox.ui;

import java.util.List;
import java.util.Optional;

import groovebox.model.Beat;
import groovebox.model.FourBarPhrase;
import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import groovebox.model.Tick;
import groovebox.services.SoundService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GrooveBoxController {
	@FXML
	private Label welcomeText;

	private final SoundService soundService;

	private final Beat beat = new Beat(List.of(new FourBarPhrase(
			new QuarterNote(null, null, null, null),
			new QuarterNote(null, null, null, null),
			new QuarterNote(null, null, null, null),
			new QuarterNote(null, null, null, null)
	)));

	public GrooveBoxController() {
		try {
			soundService = new SoundService();
			soundService.defineTrack(beat.createTrackDate());
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
			soundService.start();
			welcomeText.setText("Music started");
		}
	}

	@FXML
	protected void onInstrumentChanged(ActionEvent actionEvent) {
		CheckBox node = (CheckBox) actionEvent.getSource();
		int row = Optional.ofNullable(GridPane.getRowIndex(node)).orElse(0);
		int col = Optional.ofNullable(GridPane.getColumnIndex(node)).orElse(0) - 1;

		QuarterNote quarterNote = getQuarterNote(col);
		Tick tick = createTick(node.isSelected(), row);
		setTick(col, quarterNote, tick);

		try {
			soundService.defineTrack(beat.createTrackDate());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private QuarterNote getQuarterNote(int col) {
		return switch (col / 4) {
			case 0 -> beat.phrases().get(0).note1();
			case 1 -> beat.phrases().get(0).note2();
			case 2 -> beat.phrases().get(0).note3();
			default -> beat.phrases().get(0).note4();
		};
	}

	private static Tick createTick(boolean selected, int row) {
		return selected ? row == Instrument.ACOUSTIC_BASS_DRUM.ordinal() ?
				new Tick(Instrument.ACOUSTIC_BASS_DRUM, 120) :
				new Tick(Instrument.ELECTRIC_SNARE, 120)
				: null;
	}

	private static void setTick(int col, QuarterNote quarterNote, Tick tick) {
		switch (col % 4) {
			case 0:
				quarterNote.setTick1(tick);
				break;
			case 1:
				quarterNote.setTick2(tick);
				break;
			case 2:
				quarterNote.setTick3(tick);
				break;
			case 3:
				quarterNote.setTick4(tick);
				break;
		}
	}

	public void close() {
		soundService.close();
	}
}
