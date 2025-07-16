package groovebox.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Beat(List<FourBarPhrase> phrases) {
	public List<Tick> ticks() {
		return phrases().stream()
				.flatMap(phrase -> Stream.of(phrase.note1(), phrase.note2(), phrase.note3(), phrase.note4()))
				.flatMap(note -> note != null ? Stream.of(note.getTick1(), note.getTick2(), note.getTick3(), note.getTick4()) : Stream.empty())
				.collect(Collectors.toList());
	}
}
