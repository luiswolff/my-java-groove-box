package groovebox.ui;

import java.util.function.Supplier;

import groovebox.service.Beat;
import groovebox.service.SoundService;
import groovebox.service.TickPosition;
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

	@FXML
	private PhrasePagination phrasePagination;

	private final GrooveBoxModel grooveBoxModel = new GrooveBoxModel(this::handleModelChanged);

	private final AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			TickPosition tickPosition = soundService.getTickPosition(grooveBoxModel.phrasesProperty().get());
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
		grooveBoxModel.setBeat(new Beat());

		instrumentGridPane.apply(grooveBoxModel, this::handleModelChanged);
		loopCountSpinner.apply(grooveBoxModel);
		tempoSpinner.apply(grooveBoxModel);
		infinityLoopCheckBox.apply(grooveBoxModel);
		sampleBeatMenu.apply(grooveBoxModel);
		playStopButton.apply(grooveBoxModel);
		phrasePagination.apply(grooveBoxModel);
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

	@FXML
	protected void onAddPhraseButtonClicked() {
		grooveBoxModel.addNewPhrase();
		handleModelChanged();
	}

	@FXML
	protected void onRemovePhraseButtonClicked() {
		grooveBoxModel.removeCurrentPhrase();
		handleModelChanged();
	}

	public void close() {
		soundService.close();
		timer.stop();
	}
}
