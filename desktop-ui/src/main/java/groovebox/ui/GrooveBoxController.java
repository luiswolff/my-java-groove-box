package groovebox.ui;

import java.util.function.Supplier;

import groovebox.service.BeatFactory;
import groovebox.service.SoundService;
import groovebox.ui.model.GrooveBoxModel;
import groovebox.ui.timer.TickPositionTimer;
import javafx.beans.property.BooleanProperty;
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

	@FXML
	private PhrasePagination phrasePagination;

	private final SoundService soundService = new SoundService();
	private final GrooveBoxModel grooveBoxModel = new GrooveBoxModel(soundService);
	private final TickPositionTimer tickPositionTimer =
			new TickPositionTimer(grooveBoxModel.getSoundControlModel());

	@FXML
	public void initialize() {
		grooveBoxModel.setBeat(BeatFactory.builder().build());

		instrumentGridPane.apply(grooveBoxModel);
		loopCountSpinner.apply(grooveBoxModel);
		tempoSpinner.apply(grooveBoxModel);
		infinityLoopCheckBox.apply(grooveBoxModel);
		sampleBeatMenu.apply(grooveBoxModel);
		playStopButton.apply(grooveBoxModel);
		phrasePagination.apply(grooveBoxModel);

	}

	@FXML
	protected void onStartStopButtonClicked() {
		BooleanProperty booleanProperty = grooveBoxModel.getSoundControlModel().runningProperty();
		booleanProperty.set(!booleanProperty.get());
	}

	@FXML
	protected void onSampleBeatChanged(ActionEvent event) {
		Object source = event.getSource();
		BeatFactory beat = getBeat(source);
		grooveBoxModel.setBeat(beat);
	}

	private BeatFactory getBeat(Object source) {
		Supplier<BeatFactory> beatSupplier;
		if (source instanceof SampleBeatMenu) {
			//noinspection unchecked
			beatSupplier = (Supplier<BeatFactory>) ((SampleBeatMenu) source).getUserData();
		} else {
			//noinspection unchecked
			beatSupplier = (Supplier<BeatFactory>) ((SampleBeatMenu.SampleBeatMenuItem) source).getUserData();
		}
		BeatFactory beatFactory = beatSupplier.get();
		beatFactory.apply(soundService.getSoundControl());
		return beatFactory;
	}

	@FXML
	protected void onAddPhraseButtonClicked() {
		grooveBoxModel.addNewPhrase();
	}

	@FXML
	protected void onRemovePhraseButtonClicked() {
		grooveBoxModel.removeCurrentPhrase();
	}

	public void close() {
		soundService.close();
		tickPositionTimer.close();
	}
}
