package groovebox.service;

import java.util.List;

public class FourBarPhrase {
	private final QuarterNote note1 = new QuarterNote();
	private final QuarterNote note2 = new QuarterNote();
	private final QuarterNote note3 = new QuarterNote();
	private final QuarterNote note4 = new QuarterNote();
	public List<QuarterNote> getQuarterNotes() {
		return List.of(note1, note2, note3, note4);
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

	public QuarterNote getQuarterNote(int noteIndex) {
		return switch (noteIndex) {
			case 0 -> getNote1();
			case 1 -> getNote2();
			case 2 -> getNote3();
			default -> getNote4();
		};
	}
}
