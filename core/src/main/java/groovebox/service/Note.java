package groovebox.service;

import java.util.List;

public interface Note {
	List<? extends Tick> getTicks();
}
