package groovebox.adapter;

import javax.sound.midi.Sequencer;

public class JavaMidiControlData {
	private final Sequencer sequencer;
	private int lastLoopCount;
	private float tempoInBPMCache;

	public JavaMidiControlData(Sequencer sequencer) {
		this.sequencer = sequencer;
		this.lastLoopCount = sequencer.getLoopCount();
		this.tempoInBPMCache = sequencer.getTempoInBPM();
	}

	public long getTickPosition() {
		return sequencer.getTickPosition();
	}

	public void setTickPosition(long tick) {
		sequencer.setTickPosition(tick);
	}

	public boolean isLoopContinuously() {
		return sequencer.getLoopCount() == Sequencer.LOOP_CONTINUOUSLY;
	}

	public void setLoopContinuously(boolean loopContinuously) {
		if (loopContinuously) {
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		} else {
			sequencer.setLoopCount(lastLoopCount);
		}
	}

	public void setLoopCount(int count) {
		if (!isLoopContinuously()) {
			int sequencerLoopCount = count - 1;
			this.lastLoopCount = sequencerLoopCount;
			sequencer.setLoopCount(sequencerLoopCount);
		}
	}

	public int getLoopCount() {
		if (isLoopContinuously()) {
			return lastLoopCount;
		} else {
			return sequencer.getLoopCount() + 1;
		}
	}

	public float getTempoInBPM() {
		return sequencer.getTempoInBPM();
	}

	public void setTempoInBPM(float bpm) {
		tempoInBPMCache = bpm;
		sequencer.setTempoInBPM(bpm);
	}

	void resetTempoInBPM() {
		sequencer.setTempoInBPM(tempoInBPMCache);
	}

	public boolean isRunning() {
		return sequencer.isRunning();
	}

	public void setRunning(Boolean running) {
		if (Boolean.FALSE.equals(running)) {
			sequencer.stop();
			sequencer.setTickPosition(0);
		} else if (Boolean.TRUE.equals(running)) {
			// for some reason the tempo is always reset when changing loop count
			resetTempoInBPM();
			sequencer.start();
		}
	}
}
