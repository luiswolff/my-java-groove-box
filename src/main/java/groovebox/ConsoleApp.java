package groovebox;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import groovebox.model.Beat;
import groovebox.model.FourBarPhrase;
import groovebox.model.Instrument;
import groovebox.model.QuarterNote;
import groovebox.model.Tick;
import groovebox.services.SoundService;

public class ConsoleApp {
	public static void main(String[] args) throws Exception {
		SoundService soundService = new SoundService();
		QuarterNote note1 = new QuarterNote(new Tick(Instrument.ACOUSTIC_BASS_DRUM, 120),
				null,
				null,
				new Tick(Instrument.ELECTRIC_SNARE, 100));
		QuarterNote note2 = new QuarterNote(new Tick(Instrument.ACOUSTIC_BASS_DRUM, 120),
				null,
				new Tick(Instrument.ELECTRIC_SNARE, 100),
				null);
		soundService.play(new Beat(List.of(new FourBarPhrase(
				note1,
				note2,
				note1,
				note2
		))));

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

		Runtime.getRuntime().addShutdownHook(new Thread(soundService::stop));
	}
}
