import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

class SoundService {
	final Sequencer sequencer;
	final Sequence sequence;
	final Track track;
	SoundService() throws Exception {
		sequencer = MidiSystem.getSequencer();
		sequence = new Sequence(Sequence.PPQ, 4);
		track = sequence.createTrack();
	}

	void play(Beat beat) throws Exception {
		for (Integer tick : beat.ticks()) {
			addTick(tick);
		}

		sequencer.setSequence(sequence);
		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		sequencer.open();
		sequencer.setTempoInBPM(94.0f);
		sequencer.start();
	}

	void stop() {
		if (sequencer.isOpen()) {
			sequencer.stop();
			sequencer.close();
		}
	}

	private void addTick(int tick) throws InvalidMidiDataException {
		addEvent(ShortMessage.NOTE_ON, 35, tick + 1);
		addEvent(ShortMessage.NOTE_OFF, 0, tick + 2);
	}

	private void addEvent(int type, int num, int tick) throws InvalidMidiDataException {
		ShortMessage message = new ShortMessage();
		message.setMessage(type, 9, num, 100);
		track.add(new MidiEvent(message, tick));
	}
}
