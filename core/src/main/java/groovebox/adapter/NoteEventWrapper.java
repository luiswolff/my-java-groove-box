package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class NoteEventWrapper extends MidiEventWrapper {
	private final MidiEventWrapper noteOffEventWrapper;
	public NoteEventWrapper(Track track, int instrument, int velocity, long tickVal) throws InvalidMidiDataException {
		super(track, ShortMessage.NOTE_ON, instrument, velocity, tickVal);
		noteOffEventWrapper = new MidiEventWrapper(track, ShortMessage.NOTE_OFF, instrument, velocity, tickVal + 1);
	}

	@Override
	public void setTickVal(long tickVal) {
		super.setTickVal(tickVal);
		noteOffEventWrapper.setTickVal(tickVal + 1);
	}

	@Override
	public void setVelocity(int velocity) throws InvalidMidiDataException {
		super.setVelocity(velocity);
		noteOffEventWrapper.setVelocity(velocity);
	}

	@Override
	public void setAddedToTrack(boolean addedToTrack) {
		super.setAddedToTrack(addedToTrack);
		noteOffEventWrapper.setAddedToTrack(addedToTrack);
	}
}
