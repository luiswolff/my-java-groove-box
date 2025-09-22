package groovebox.adapter;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class JavaMidiSequence {
	private final Track track;
	private final Map<InstrumentPosition, JavaMidiNote> notes = new HashMap<>();
	private final Sequence sequence;

	public JavaMidiSequence() {
		try {
			this.sequence = new Sequence(Sequence.PPQ, 4);
			this.track = this.sequence.createTrack();
		} catch (InvalidMidiDataException e) {
			throw new IllegalStateException(e);
		}
	}

	public void setTrackEnd(int tickVal) {
		track.get(track.size() - 1).setTick(tickVal);
	}

	public JavaMidiNote getNote(int instrument, int tickVal) {
		return notes.computeIfAbsent(new InstrumentPosition(instrument, tickVal), this::createNote);
	}

	private JavaMidiNote createNote(InstrumentPosition instrumentPosition) {
		try {
			return new JavaMidiNote(track, instrumentPosition.instrument(), 100, instrumentPosition.tickVal());
		} catch (InvalidMidiDataException e) {
			throw new IllegalArgumentException(e);
		}
	}

	Sequence getSequence() {
		return sequence;
	}

	private record InstrumentPosition(int instrument, int tickVal) {}
}
