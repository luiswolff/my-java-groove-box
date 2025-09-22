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
		this.lastLoopCount = count;
		sequencer.setLoopCount(count);
	}

	public int getLoopCount() {
		return sequencer.getLoopCount();
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
}
