package groovebox.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import groovebox.services.NoteDataBytes;

public class QuarterNote {
	private final List<Tick> ticks1 = new LinkedList<>();
	private final List<Tick> ticks2 = new LinkedList<>();
	private final List<Tick> ticks3 = new LinkedList<>();
	private final List<Tick> ticks4 = new LinkedList<>();

	Stream<NoteDataBytes[]> getNoteDataBytesStream() {
		return Stream.of(
				toNoteDataBytes(ticks1),
				toNoteDataBytes(ticks2),
				toNoteDataBytes(ticks3),
				toNoteDataBytes(ticks4));
	}

	private NoteDataBytes[] toNoteDataBytes(List<Tick> ticks) {
		return ticks.stream()
				.map(tick -> new NoteDataBytes(tick.instrument().value, tick.velocity()))
				.toArray(NoteDataBytes[]::new);
	}

	public void setTick(Tick tick, int tickIndex) {
		List<Tick> ticks = getTicks(tickIndex);
		ticks.add(tick);
	}

	public void removeTick(Tick tick, int tickIndex) {
		List<Tick> ticks = getTicks(tickIndex);
		ticks.remove(tick);
	}

	public boolean hasTick(Tick tick, int tickIndex) {
		List<Tick> ticks = getTicks(tickIndex);
		return ticks.contains(tick);
	}

	private List<Tick> getTicks(int tickIndex) {
		return switch (tickIndex) {
			case 0 -> ticks1;
			case 1 -> ticks2;
			case 2 -> ticks3;
			default -> ticks4;
		};
	}
}
