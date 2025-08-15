package groovebox.service;

import java.util.List;
import java.util.stream.Stream;

import groovebox.adapter.NoteDataBytes;
import groovebox.adapter.TrackData;

class BeatToTrackDataMapper {
	static TrackData createTrackData(Beat beat) {
		return new TrackData(
				beat.getResolution(),
				noteDataTable(beat.getPhrases()),
				beat.getLoopCount(),
				beat.getTempoInBPM()
		);
	}

	private static NoteDataBytes[][] noteDataTable(List<FourBarPhrase> phrases) {
		return phrases.stream()
				.map(FourBarPhrase::getQuarterNotes)
				.flatMap(List::stream)
				.map(QuarterNote::getTicks)
				.flatMap(BeatToTrackDataMapper::getNoteDataBytesStream)
				.toArray(NoteDataBytes[][]::new);
	}

	private static Stream<NoteDataBytes[]> getNoteDataBytesStream(List<List<Tick>> ticks) {
		return ticks.stream().map(BeatToTrackDataMapper::toNoteDataBytes);
	}

	private static NoteDataBytes[] toNoteDataBytes(List<Tick> ticks) {
		return ticks.stream()
				.map(tick -> new NoteDataBytes(tick.getInstrument().value, tick.getVelocity()))
				.toArray(NoteDataBytes[]::new);
	}
}
