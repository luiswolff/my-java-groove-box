package groovebox.service;

import groovebox.adapter.JavaMidiSoundAdapter;

public class SoundService {
	private final JavaMidiSoundAdapter soundAdapter = new JavaMidiSoundAdapter();
	private final SoundControl soundControl = new SoundControl(soundAdapter.getControlData());

	public void setBeat(Beat beat) {
		soundControl.setPhrases(beat.getPhrases());
		soundAdapter.apply(beat.getSequence());
	}

	public SoundControl getSoundControl() {
		return soundControl;
	}

	public void close() {
		soundAdapter.close();
	}
}
