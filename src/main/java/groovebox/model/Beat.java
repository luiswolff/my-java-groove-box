package groovebox.model;

import java.util.List;
import java.util.stream.Stream;

import groovebox.services.NoteDataBytes;
import groovebox.services.TrackData;

public record Beat(List<FourBarPhrase> phrases) {

	public Beat() {
		this(List.of(new FourBarPhrase(
				new QuarterNote(null, null, null, null),
				new QuarterNote(null, null, null, null),
				new QuarterNote(null, null, null, null),
				new QuarterNote(null, null, null, null)
		)));
	}

	public TrackData createTrackData() {
		return new TrackData(
				resolution(),
				noteDataTable(),
				loopCount(),
				tempoInBPM()
		);
	}

	public int resolution() {
		return 4; // four tick positions per note
	}

	public NoteDataBytes[][] noteDataTable() {
		return phrases().stream()
				.flatMap(phrase -> Stream.of(phrase.note1(), phrase.note2(), phrase.note3(), phrase.note4()))
				.flatMap(note -> note != null ? Stream.of(note.getTick1(), note.getTick2(), note.getTick3(), note.getTick4()) : Stream.empty())
				.map(tick -> tick != null ? new NoteDataBytes[] {new NoteDataBytes(tick.instrument().value, tick.velocity())} : new NoteDataBytes[0])
				.toArray(NoteDataBytes[][]::new);
	}

	public int loopCount() {
		return -1; // Sequencer.LOOP_CONTINUOUSLY
	}

	public float tempoInBPM() {
		return 94.0f;
	}

	public void defineTick(Instrument instrument, boolean selected, int noteIndex, int tickIndex) {
		QuarterNote quarterNote = getQuarterNote(noteIndex);
		Tick tick = createTick(selected, instrument);
		setTick(quarterNote, tick, tickIndex);
	}

	private QuarterNote getQuarterNote(int noteIndex) {
		return switch (noteIndex) {
			case 0 -> phrases().get(0).note1();
			case 1 -> phrases().get(0).note2();
			case 2 -> phrases().get(0).note3();
			default -> phrases().get(0).note4();
		};
	}

	private static Tick createTick(boolean selected, Instrument instrument) {
		return selected ? new Tick(instrument, 120) : null;
	}

	private static void setTick(QuarterNote quarterNote, Tick tick, int tickIndex) {
		switch (tickIndex) {
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
}
