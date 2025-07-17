package groovebox.model;

import java.util.List;
import java.util.stream.Stream;

import groovebox.services.NoteDataBytes;
import groovebox.services.TrackData;

public record Beat(List<FourBarPhrase> phrases) {

	public TrackData createTrackDate() {
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
}
