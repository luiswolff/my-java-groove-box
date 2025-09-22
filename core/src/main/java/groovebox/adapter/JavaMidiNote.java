package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class JavaMidiNote extends JavaMidiEvent {
	private final JavaMidiEvent noteOffEvent;
	JavaMidiNote(Track track, int instrument, int velocity, long tickVal) throws InvalidMidiDataException {
		super(track, ShortMessage.NOTE_ON, instrument, velocity, tickVal);
		noteOffEvent = new JavaMidiEvent(track, ShortMessage.NOTE_OFF, instrument, velocity, tickVal + 1);
	}

	@Override
	public void setTickVal(long tickVal) {
		super.setTickVal(tickVal);
		noteOffEvent.setTickVal(tickVal + 1);
	}

	@Override
	public void setVelocity(int velocity) {
		super.setVelocity(velocity);
		noteOffEvent.setVelocity(velocity);
	}

	@Override
	public void setAddedToTrack(boolean addedToTrack) {
		super.setAddedToTrack(addedToTrack);
		noteOffEvent.setAddedToTrack(addedToTrack);
	}
}
