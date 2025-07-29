package groovebox.model;

import java.util.List;

import groovebox.services.NoteDataBytes;
import groovebox.services.TrackData;

public class Beat {

	private final List<FourBarPhrase> phrases;
	private final int resolution = 4; // four tick positions per note
	private int loopCount = -1; // Sequencer.LOOP_CONTINUOUSLY
	private float tempoInBPM = 120.0f;

	public Beat() {
		phrases = List.of(new FourBarPhrase());
	}

	public TrackData createTrackData() {
		return new TrackData(
				resolution,
				noteDataTable(),
				loopCount,
				tempoInBPM
		);
	}

	public int getResolution() {
		return resolution;
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

	public NoteDataBytes[][] noteDataTable() {
		return phrases.stream()
				.flatMap(FourBarPhrase::getQuarterNoteStream)
				.flatMap(QuarterNote::getNoteDataBytesStream)
				.toArray(NoteDataBytes[][]::new);
	}

	public void defineTick(Instrument instrument, int noteIndex, int tickIndex) {
		QuarterNote quarterNote = phrases.get(0).getQuarterNote(noteIndex);
		quarterNote.setTick(new Tick(instrument, 120), tickIndex);
	}

	public void removeTick(Instrument instrument, int noteIndex, int tickIndex) {
		QuarterNote quarterNote = phrases.get(0).getQuarterNote(noteIndex);
		quarterNote.removeTick(new Tick(instrument, 0), tickIndex);
	}

	public boolean hasTick(Instrument instrument, int noteIndex, int tickIndex) {
		QuarterNote quarterNote = phrases.get(0).getQuarterNote(noteIndex);
		return quarterNote.hasTick(new Tick(instrument, 0), tickIndex);
	}
}
