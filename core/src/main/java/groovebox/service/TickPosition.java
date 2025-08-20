package groovebox.service;

import java.util.List;

public record TickPosition(int currentPhrase, int phrasePosition) {
	static TickPosition from(List<Phrase> phrases, int tickPosition) {
		int ticksPerPhrase = calculateTicksPerPhrase(phrases);
		int currentPhrase = tickPosition / ticksPerPhrase;
		int phrasePosition = tickPosition % ticksPerPhrase;
		return new TickPosition(currentPhrase, phrasePosition);
	}

	/*
	 * This method assumes that all phrases have the same amound of note
	 * and all notes have the same amount of ticks.
	 */
	private static int calculateTicksPerPhrase(List<Phrase> phrases) {
		int countTicks = phrases.stream()
				.map(Phrase::getNotes)
				.flatMap(List::stream)
				.map(Note::getTicks)
				.mapToInt(List::size).sum();
		return countTicks / phrases.size();
	}
}
