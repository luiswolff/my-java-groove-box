package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class JavaMidiSoundAdapter implements AutoCloseable {
	private final Sequencer sequencer;
	private final JavaMidiControlData controlData;

	public JavaMidiSoundAdapter() {
		try {
			sequencer = MidiSystem.getSequencer();
			controlData = new JavaMidiControlData(sequencer);
			sequencer.addMetaEventListener(meta -> {
				if (meta.getType() == 47) {
					controlData.setTickPosition(0L);
				}
			});
			sequencer.open();
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException("could not initialize Sequencer", e);
		}
	}

	public void start() {
		// for some reason the tempo is always reset when changing loop count
		controlData.resetTempoInBPM();
		sequencer.start();
	}

	public void stop() {
		sequencer.stop();
		sequencer.setTickPosition(0);
	}

	public void apply(JavaMidiSequence javaMidiSequence) {
		try {
			sequencer.setSequence(javaMidiSequence.getSequence());
		} catch (InvalidMidiDataException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public JavaMidiControlData getControlData() {
		return controlData;
	}

	public boolean isRunning() {
		return sequencer.isRunning();
	}

	@Override
	public void close() {
		if (sequencer.isOpen()) {
			sequencer.close();
		}
	}
}
