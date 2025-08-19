package groovebox.service;

import java.util.ArrayList;
import java.util.List;

public class Beat {

	private final List<FourBarPhrase> phrases = new ArrayList<>();
	private int loopCount = -1; // Sequencer.LOOP_CONTINUOUSLY
	private float tempoInBPM = 120.0f;

	public int getResolution() {
		// four tick positions per note
		return 4;
	}

	public float getTempoInBPM() {
		return tempoInBPM;
	}

	public void setTempoInBPM(float tempoInBPM) {
		this.tempoInBPM = tempoInBPM;
	}

	public int getLoopCount() {
		return loopCount;
	}

	public void setLoopCount(Integer loopCount) {
		this.loopCount = loopCount;
	}

	public boolean isInfinityLoopCount() {
		return loopCount < 0;
	}

	public List<FourBarPhrase> getPhrases() {
		return phrases;
	}

	void defineTick(Instrument instrument, int noteIndex, int tickIndex) {
		if (phrases.isEmpty()) {
			phrases.add(new FourBarPhrase());
		}
		FourBarPhrase fourBarPhrase = phrases.getFirst();
		List<QuarterNote> quarterNotes = fourBarPhrase.getQuarterNotes();
		QuarterNote quarterNote = quarterNotes.get(noteIndex);
		List<List<Tick>> tickTable = quarterNote.getTicks();
		List<Tick> ticks = tickTable.get(tickIndex);
		ticks.add(new Tick(instrument, 120));
	}

}
