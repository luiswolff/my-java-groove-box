package groovebox.ui.model;

import java.util.List;

import groovebox.service.Beat;
import groovebox.service.Phrase;
import groovebox.service.SoundService;
import groovebox.service.TickPosition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PlayedBeatModel {
	private final ObjectProperty<Beat> beat = new SimpleObjectProperty<>();
	private final ObjectProperty<Phrase> phrase = new SimpleObjectProperty<>();
	private final IntegerProperty phrasesSize = new SimpleIntegerProperty();
	private final IntegerProperty phraseIndex = new SimpleIntegerProperty();
	private final IntegerProperty highlightedTick = new SimpleIntegerProperty(-1);
	private final SoundService soundService;

	public PlayedBeatModel(SoundService soundService) {
		this.soundService = soundService;
		beat.subscribe(this::beatChanged);
		phraseIndex.subscribe(this::onPhraseIndexChanged);
	}

	private void beatChanged(Beat beat) {
		if (beat == null) {
			return;
		}
		soundService.setBeat(beat);
		phraseIndex.setValue(0);
		List<Phrase> beatPhrases = beat.getPhrases();
		phrasesSize.setValue(beatPhrases.size());
		phrase.setValue(beatPhrases.get(phraseIndex.get()));
	}

	public ObjectProperty<Beat> beatProperty() {
		return beat;
	}

	public ObjectProperty<Phrase> phraseProperty() {
		return phrase;
	}

	public IntegerProperty phrasesSizeProperty() {
		return phrasesSize;
	}

	public IntegerProperty phraseIndexProperty() {
		return phraseIndex;
	}

	public IntegerProperty highlightedTickProperty() {
		return highlightedTick;
	}

	public void addNewPhrase() {
		beat.get().addPhrase();
		List<Phrase> beatPhrases = beat.get().getPhrases();
		phrasesSize.setValue(beatPhrases.size());
		phraseIndex.set(beatPhrases.size() - 1);
	}

	public void removeCurrentPhrase() {
		beat.get().removePhrase(phraseIndex.get());
		List<Phrase> beatPhrases = beat.get().getPhrases();
		phrasesSize.setValue(beatPhrases.size());
		if (beatPhrases.isEmpty()) {
			addNewPhrase();
			onPhraseIndexChanged();
		} else {
			if (phraseIndex.get() == 0 ) {
				onPhraseIndexChanged();
			} else {
				phraseIndex.subtract(1);
			}
		}
	}

	private void onPhraseIndexChanged() {
		int index = phraseIndex.get();
		phrase.set(beat.get().getPhrases().get(index));
	}

	public void bind(ObjectProperty<TickPosition> tickPosition) {
		tickPosition.subscribe(position -> {
			if (position != null && phraseIndex.get() == position.currentPhrase()) {
				highlightedTick.set(position.phrasePosition());
			} else {
				highlightedTick.set(-1);
			}
		});
	}
}
