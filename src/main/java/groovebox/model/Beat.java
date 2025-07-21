package groovebox.model;

import java.util.List;
import java.util.stream.Stream;

import groovebox.services.NoteDataBytes;
import groovebox.services.TrackData;

public class Beat {

	private final List<FourBarPhrase> phrases;
	private final int resolution = 4; // four tick positions per note
	private final int loopCount = -1; // Sequencer.LOOP_CONTINUOUSLY
	private float tempoInBPM = 94.0f;

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

	public void setTempoInBPM(float tempoInBPM) {
		this.tempoInBPM = tempoInBPM;
	}

	public NoteDataBytes[][] noteDataTable() {
		return phrases.stream()
				.flatMap(FourBarPhrase::getQuarterNoteStream)
				.flatMap(note -> note != null ? note.getTickStream() : Stream.empty())
				.map(tick -> tick != null ? new NoteDataBytes[] {new NoteDataBytes(tick.instrument().value, tick.velocity())} : new NoteDataBytes[0])
				.toArray(NoteDataBytes[][]::new);
	}

	public void defineTick(Instrument instrument, int noteIndex, int tickIndex) {
		QuarterNote quarterNote = phrases.get(0).getQuarterNote(noteIndex);
		quarterNote.setTick(new Tick(instrument, 120), tickIndex);
	}

	public void removeTick(Instrument instrument, int noteIndex, int tickIndex) {
		QuarterNote quarterNote = phrases.get(0).getQuarterNote(noteIndex);
		quarterNote.setTick(null, tickIndex);
	}

}
