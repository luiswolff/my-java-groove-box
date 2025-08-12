package groovebox;

import java.util.Collections;
import java.util.Scanner;

import groovebox.service.SampleBeatFactory;
import groovebox.service.SoundService;

public class ConsoleApp {
	public static void main(String[] args) {
		SoundService soundService = new SoundService();
		soundService.defineTrack(SampleBeatFactory.REGGAETON.createBeat());

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
