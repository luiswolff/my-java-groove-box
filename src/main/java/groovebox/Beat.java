package groovebox;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

record Beat(List<FourBarPhrase> phrases) {
	public List<Tick> ticks() {
		return phrases().stream()
				.flatMap(phrase -> Stream.of(phrase.note1(), phrase.note2(), phrase.note3(), phrase.note4()))
				.flatMap(note -> note != null ? Stream.of(note.tick1(), note.tick2(), note.tick3(), note.tick4()) : Stream.empty())
				.collect(Collectors.toList());
	}
}

record FourBarPhrase(QuarterNote note1, QuarterNote note2, QuarterNote note3, QuarterNote note4) {
}

record QuarterNote(Tick tick1, Tick tick2,  Tick tick3,  Tick tick4) {
}

record Tick(Instrument instrument, int velocity) {}

enum Instrument {
	ACOUSTIC_BASS_DRUM(35),
	ELECTRIC_SNARE(40);
	final int value;

	Instrument(int value) {
		this.value = value;
	}
}