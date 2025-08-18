package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

class SequenceFactory {
	static Sequence create(TrackData trackData) throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, trackData.resolution());
		applyTrackData(TrackBuilder.createFrom(sequence), trackData.noteDataTable());
		return sequence;
	}

	private static void applyTrackData(TrackBuilder trackBuilder, NoteDataBytes[][] noteDataBytesTable) throws InvalidMidiDataException {
		for (int i = 0; i < noteDataBytesTable.length; i++) {
			for (NoteDataBytes noteDataBytes : noteDataBytesTable[i]) {
				trackBuilder.addNote(noteDataBytes, i);
			}
		}
		trackBuilder.finish(noteDataBytesTable.length);
	}
}
