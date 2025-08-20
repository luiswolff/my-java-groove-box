package groovebox.service;

import java.util.LinkedList;
import java.util.List;

public class Note {
	private final List<List<Tick>> ticks = List.of(
			new LinkedList<>(),
			new LinkedList<>(),
			new LinkedList<>(),
			new LinkedList<>()
	);

	public List<List<Tick>> getTicks() {
		return ticks;
	}

}
