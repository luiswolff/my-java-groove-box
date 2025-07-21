package groovebox.model;

import java.util.stream.Stream;

public class FourBarPhrase {
	private final QuarterNote note1 = new QuarterNote(null, null, null, null);
	private final QuarterNote note2 = new QuarterNote(null, null, null, null);
	private final QuarterNote note3 = new QuarterNote(null, null, null, null);
	private final QuarterNote note4 = new QuarterNote(null, null, null, null);
	Stream<QuarterNote> getQuarterNoteStream() {
		return Stream.of(note1, note2, note3, note4);
	}

	public QuarterNote getNote1() {
		return note1;
	}

	public QuarterNote getNote2() {
		return note2;
	}

	public QuarterNote getNote3() {
		return note3;
	}

	public QuarterNote getNote4() {
		return note4;
	}

	QuarterNote getQuarterNote(int noteIndex) {
		return switch (noteIndex) {
			case 0 -> getNote1();
			case 1 -> getNote2();
			case 2 -> getNote3();
			default -> getNote4();
		};
	}
}
