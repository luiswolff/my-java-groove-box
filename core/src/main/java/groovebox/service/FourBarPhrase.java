package groovebox.service;

import java.util.List;

public class FourBarPhrase {
	private final List<QuarterNote> notes = List.of(
			new QuarterNote(),
			new QuarterNote(),
			new QuarterNote(),
			new QuarterNote()
	);

	public List<QuarterNote> getQuarterNotes() {
		return notes;
	}
}
