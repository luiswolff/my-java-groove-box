package groovebox.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import groovebox.adapter.JavaMidiControlData;

public class SoundControl {
	private final JavaMidiControlData controlData;
	private List<Phrase> phrases = Collections.emptyList();

	SoundControl(JavaMidiControlData controlData) {
		this.controlData = controlData;
	}

	void setPhrases(List<Phrase> phrases) {
		this.phrases = Objects.requireNonNull(phrases);
	}

	public TickPosition getTickPosition() {
		if (isRunning()) {
			return TickPosition.from(phrases, (int) controlData.getTickPosition());
		}
		return null;
	}

	public boolean isLoopContinuously() {
		return controlData.isLoopContinuously();
	}

	public void setLoopContinuously(boolean loopContinuously) {
		controlData.setLoopContinuously(loopContinuously);
	}

	public void setLoopCount(int count) {
		controlData.setLoopCount(count);
	}

	public int getLoopCount() {
		return controlData.getLoopCount();
	}

	public int getTempoInBPM() {
		return (int) controlData.getTempoInBPM();
	}

	public void setTempoInBPM(int bpm) {
		controlData.setTempoInBPM(bpm);
	}

	public boolean isRunning() {
		return controlData.isRunning();
	}
}
