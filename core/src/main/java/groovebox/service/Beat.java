package groovebox.service;

import java.util.List;

import groovebox.adapter.NoteDataBytes;

public class Beat {

	private final List<FourBarPhrase> phrases;
	private int loopCount = -1; // Sequencer.LOOP_CONTINUOUSLY
	private float tempoInBPM = 120.0f;

	public Beat() {
		phrases = List.of(new FourBarPhrase());
	}

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

	NoteDataBytes[][] noteDataTable() {
		return phrases.stream()
				.map(FourBarPhrase::getQuarterNotes)
				.flatMap(List::stream)
				.flatMap(QuarterNote::getNoteDataBytesStream)
				.toArray(NoteDataBytes[][]::new);
	}

	public List<FourBarPhrase> getPhrases() {
		return phrases;
	}

	public void defineTick(Instrument instrument, int noteIndex, int tickIndex) {
		QuarterNote quarterNote = phrases.getFirst().getQuarterNote(noteIndex);
		quarterNote.setTick(new Tick(instrument, 120), tickIndex);
	}

}
