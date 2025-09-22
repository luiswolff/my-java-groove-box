package groovebox.service;

import java.util.List;

import groovebox.adapter.JavaMidiSoundAdapter;

public class SoundService {
	private final JavaMidiSoundAdapter soundAdapter = new JavaMidiSoundAdapter();
	private final SoundControl soundControl = new SoundControl(soundAdapter.getControlData());

	public void start() {
		soundAdapter.start();
	}

	public void stop() {
		soundAdapter.stop();
	}

	public void setBeat(Beat<? extends List<Phrase>> beat) {
		soundControl.setPhrases(beat.getPhrases());
		soundAdapter.apply(beat.getSequence());
	}

	public SoundControl getSoundControl() {
		return soundControl;
	}

	public boolean isRunning() {
		return soundAdapter.isRunning();
	}

	public void close() {
		soundAdapter.close();
	}
}
