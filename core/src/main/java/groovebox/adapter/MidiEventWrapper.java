package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

class MidiEventWrapper {
	private final Track track;
	private final MidiEvent eventOn;
	private final ShortMessage messageEventOn;
	private boolean addedToTrack = false;
	MidiEventWrapper(Track track, int type, int instrument, int velocity, long tickVal) throws InvalidMidiDataException {
		this.track = track;
		messageEventOn = new ShortMessage();
		messageEventOn.setMessage(type, 9, instrument, velocity);
		this.eventOn = new MidiEvent(messageEventOn, tickVal);
	}

	public boolean isAddedToTrack() {
		return addedToTrack;
	}

	public void setAddedToTrack(boolean addedToTrack) {
		if (addedToTrack) {
			track.add(eventOn);
		} else {
			track.remove(eventOn);
		}
		this.addedToTrack = addedToTrack;
	}

	public long getTickVal() {
		return eventOn.getTick();
	}

	public void setTickVal(long tickVal) {
		eventOn.setTick(tickVal);
	}

	public int getVelocity() {
		return messageEventOn.getData2();
	}

	public void setVelocity(int velocity) throws InvalidMidiDataException {
		messageEventOn.setMessage(
				messageEventOn.getCommand(),
				messageEventOn.getChannel(),
				messageEventOn.getData1(),
				velocity
		);
	}
}
