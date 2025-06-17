import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class App {
	public static void main(String[] args) throws Exception {
		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.open();
		Sequence sequence = new Sequence(Sequence.PPQ, 4);
		Track track = sequence.createTrack();
		addEvent(track, ShortMessage.PROGRAM_CHANGE, 1, 0);
		addTick(track, 0);
		addTick(track, 3);
		addTick(track, 4);
		addTick(track, 6);
		addTick(track, 8);
		addTick(track, 11);
		addTick(track, 12);
		addTick(track, 14);
		addEvent(track, ShortMessage.PROGRAM_CHANGE, 1, 16);
		sequencer.setSequence(sequence);
		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		sequencer.start();
		sequencer.setTempoInBPM(94.0f);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (sequencer.isOpen()) {
				sequencer.stop();
				sequencer.close();
			}
		}));
	}

	private static void addTick(Track track, int tick) throws InvalidMidiDataException {
		addEvent(track, ShortMessage.NOTE_ON, 35, tick);
		addEvent(track, ShortMessage.NOTE_OFF, 35, tick + 1);
	}

	private static void addEvent(Track track, int type, int num, int tick) throws InvalidMidiDataException {
		ShortMessage message = new ShortMessage();
		message.setMessage(type, 9, num, 100);
		track.add(new MidiEvent(message, tick));
	}
}
