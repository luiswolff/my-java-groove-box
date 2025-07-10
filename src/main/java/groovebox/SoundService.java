package groovebox;

import java.util.List;

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
		List<Tick> ticks = beat.ticks();
		for (int i = 0; i < ticks.size(); i++) {
			Tick tick = ticks.get(i);
			if (tick == null) continue;
			addEvent(ShortMessage.NOTE_ON, tick, i + 1);
			addEvent(ShortMessage.NOTE_OFF, tick, i + 2);
		}
		addEvent(ShortMessage.PROGRAM_CHANGE, null, ticks.size());

		sequencer.setSequence(sequence);
		sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		sequencer.open();
		sequencer.setTempoInBPM(94.0f);
	}

	void start() {
		sequencer.start();
	}

	void stop() {
		if (sequencer.isOpen()) {
			sequencer.stop();
			sequencer.close();
		}
	}

	private void addEvent(int type, Tick tick, int tickVal) throws InvalidMidiDataException {
		ShortMessage message = new ShortMessage();
		message.setMessage(type, 9, tick != null ? tick.instrument().value : 0, tick != null ? tick.velocity() : 0);
		track.add(new MidiEvent(message, tickVal));
	}

	public boolean isRunning() {
		return sequencer.isRunning();
	}

	public long getTickPosition() {
		return sequencer.getTickPosition();
	}
}
