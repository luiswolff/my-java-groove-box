package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class JavaMidiSoundAdapter implements AutoCloseable {
	private final Sequencer sequencer;
	private float bpm;

	public JavaMidiSoundAdapter(Runnable finishCallback) {
		try {
			sequencer = MidiSystem.getSequencer();
			if (finishCallback != null) {
				sequencer.addMetaEventListener(meta -> {
					if (meta.getType() == 47) {
						finishCallback.run();
					}
				});
			}
			sequencer.open();
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException("could not initialize Sequencer", e);
		}
	}

	public void defineTrack(TrackData trackData) {
		try {
			long lastTickPosition = sequencer.getTickPosition();
			sequencer.setSequence(SequenceFactory.create(trackData));
			sequencer.setLoopCount(trackData.loopCount());
			sequencer.setTickPosition(Math.min(lastTickPosition, trackData.noteDataTable().length - 1));
			sequencer.setTempoInBPM(bpm = trackData.tempoInBPM());
		} catch (InvalidMidiDataException e) {
			throw new IllegalStateException("could not create Track", e);
		}
	}

	public void start() {
		if (!isRunning()) {
			if (sequencer.getTickLength() == sequencer.getTickPosition()) {
				sequencer.setTickPosition(0);
			}
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
