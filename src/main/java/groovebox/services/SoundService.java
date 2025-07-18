package groovebox.services;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SoundService implements AutoCloseable {
	private final Sequencer sequencer;
	private float bpm;

	public SoundService() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException("could not initialize Sequencer", e);
		}
	}

	public void defineTrack(TrackData trackData) {
		try {
			Sequence sequence = createSequence(trackData);
			applyTrackData(new TrackBuilder(sequence), trackData.noteDataTable());
			applySequence(sequence, trackData);
		} catch (InvalidMidiDataException e) {
			throw new IllegalStateException("could not create Track", e);
		}
	}

	private Sequence createSequence(TrackData trackData) throws InvalidMidiDataException {
		return new Sequence(Sequence.PPQ, trackData.resolution());
	}

	private void applyTrackData(TrackBuilder trackBuilder, NoteDataBytes[][] noteDataBytesTable) throws InvalidMidiDataException {
		for (int i = 0; i < noteDataBytesTable.length; i++) {
			for (NoteDataBytes noteDataBytes : noteDataBytesTable[i]) {
				trackBuilder.addNote(noteDataBytes, i);
			}
		}
		trackBuilder.finish(noteDataBytesTable.length);
	}

	private static class TrackBuilder {
		private final Track track;
		private TrackBuilder(Sequence sequence) {
			this.track = sequence.createTrack();
		}

		private void addNote(NoteDataBytes noteDataBytes, int notePosition) throws InvalidMidiDataException {
			int noteStart = notePosition + 1;
			int noteStop = notePosition + 2;
			addEvent(ShortMessage.NOTE_ON, noteDataBytes, noteStart);
			addEvent(ShortMessage.NOTE_OFF, noteDataBytes, noteStop);
		}

		private void finish(int trackLength) throws InvalidMidiDataException {
			addEvent(ShortMessage.PROGRAM_CHANGE, NoteDataBytes.empty(), trackLength);
		}

		private void addEvent(int type, NoteDataBytes noteDataBytes, int tickVal) throws InvalidMidiDataException {
			ShortMessage message = new ShortMessage();
			message.setMessage(type, 9, noteDataBytes.firstDataByte(), noteDataBytes.secondDataByte());
			track.add(new MidiEvent(message, tickVal));
		}
	}

	private void applySequence(Sequence sequence, TrackData trackData) throws InvalidMidiDataException {
		sequencer.setSequence(sequence);
		sequencer.setLoopCount(trackData.loopCount());
		sequencer.setTempoInBPM(bpm = trackData.tempoInBPM());
	}

	public void start() {
		if (!isRunning()) {
			sequencer.start();
			sequencer.setTempoInBPM(bpm);
		}
	}

	public void stop() {
		if (isRunning()) {
			sequencer.stop();
			sequencer.setTickPosition(0);
		}
	}

	public boolean isRunning() {
		return sequencer.isRunning();
	}

	public long getTickPosition() {
		return sequencer.getTickPosition();
	}

	@Override
	public void close() {
		if (sequencer.isOpen()) {
			sequencer.close();
		}
	}
}
