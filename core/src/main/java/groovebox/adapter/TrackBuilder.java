package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

record TrackBuilder(Track track) {

	static TrackBuilder createFrom(Sequence sequence) {
		return new TrackBuilder(sequence.createTrack());
	}

	void addNote(NoteDataBytes noteDataBytes, int notePosition) throws InvalidMidiDataException {
		addEvent(ShortMessage.NOTE_ON, noteDataBytes, notePosition);
		addEvent(ShortMessage.NOTE_OFF, noteDataBytes, notePosition + 1);
	}

	void finish(int trackLength) throws InvalidMidiDataException {
		addEvent(ShortMessage.PROGRAM_CHANGE, NoteDataBytes.empty(), trackLength);
	}

	private void addEvent(int type, NoteDataBytes noteDataBytes, int tickVal) throws InvalidMidiDataException {
		ShortMessage message = new ShortMessage();
		message.setMessage(type, 9, noteDataBytes.firstDataByte(), noteDataBytes.secondDataByte());
		track.add(new MidiEvent(message, tickVal));
	}
}
