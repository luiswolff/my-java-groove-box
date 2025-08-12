package groovebox.service;

import groovebox.adapter.TrackData;

class BeatToTrackDataMapper {
	static TrackData createTrackData(Beat beat) {
		return new TrackData(
				beat.getResolution(),
				beat.noteDataTable(),
				beat.getLoopCount(),
				beat.getTempoInBPM()
		);
	}
}
