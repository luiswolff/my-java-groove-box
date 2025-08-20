package groovebox.service;

import java.util.List;

public class Phrase {
	private final List<Note> notes = List.of(
			new Note(),
			new Note(),
			new Note(),
			new Note()
	);

	public List<Note> getNotes() {
		return notes;
	}
}
