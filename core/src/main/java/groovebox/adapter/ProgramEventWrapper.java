package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class ProgramEventWrapper extends MidiEventWrapper {
	ProgramEventWrapper(Track track, long tickVal) throws InvalidMidiDataException {
		super(track, ShortMessage.PROGRAM_CHANGE, 0, 0, tickVal);
	}
}
