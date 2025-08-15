package groovebox.service;

import java.util.LinkedList;
import java.util.List;

public class QuarterNote {
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
