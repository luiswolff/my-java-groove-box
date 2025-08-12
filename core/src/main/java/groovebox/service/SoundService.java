package groovebox.service;

import groovebox.adapter.JavaMidiSoundAdapter;
import groovebox.adapter.TrackData;

public class SoundService {
	private final JavaMidiSoundAdapter soundAdapter = new JavaMidiSoundAdapter(() -> this.running = false);
	private boolean running = false;

	public void defineTrack(Beat beat) {
		TrackData trackData = BeatToTrackDataMapper.createTrackData(beat);
		soundAdapter.defineTrack(trackData);
	}

	public void start() {
		soundAdapter.start();
		running = true;
	}

	public void stop() {
		soundAdapter.stop();
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public Integer getTickPosition() {
		return running ? (int) soundAdapter.getTickPosition() : null;
	}

	public void close() {
		soundAdapter.close();
	}
}
