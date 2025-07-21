package groovebox;

import java.util.Collections;
import java.util.Scanner;

import groovebox.model.Beat;
import groovebox.model.Instrument;
import groovebox.services.SoundService;

public class ConsoleApp {
	public static void main(String[] args) {
		//noinspection resource
		SoundService soundService = new SoundService();
		Beat beat = new Beat();
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 0, 0);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 0, 3);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 1, 0);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 1, 2);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 2, 0);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 2, 3);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 3, 0);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 3, 2);
		beat.setTempoInBPM(94.0f);
		soundService.defineTrack(beat.createTrackData());

		System.out.println("Press any key to start");
		new Scanner(System.in).nextLine();
		soundService.start();

		long lastTick = -1;
		while (soundService.isRunning()) {
			long currentTick = soundService.getTickPosition();
			if (lastTick != currentTick) {
				String passedTicks = String.join("", Collections.nCopies((int) currentTick, "#"));
				String missingTicks = String.join("", Collections.nCopies(16 - (int) currentTick, " "));
				System.out.print("\r" + passedTicks + missingTicks);
				lastTick = currentTick;
			}
		}

		Runtime.getRuntime().addShutdownHook(new Thread(soundService::close));
	}
}
