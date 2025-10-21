package groovebox.adapter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
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
			sequencer.addMetaEventListener(this::onMetaEvent);
			sequencer.open();
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException("could not initialize Sequencer", e);
		}
	}

	private void onMetaEvent(MetaMessage meta) {
		if (meta.getType() == 47) {
			controlData.setTickPosition(0L);
			controlData.resetTempoInBPM();
		}
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

	@Override
	public void close() {
		if (sequencer.isOpen()) {
			sequencer.close();
		}
	}
}
