package groovebox.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import groovebox.adapter.NoteDataBytes;

public class QuarterNote {
	private final List<Tick> ticks1 = new LinkedList<>();
	private final List<Tick> ticks2 = new LinkedList<>();
	private final List<Tick> ticks3 = new LinkedList<>();
	private final List<Tick> ticks4 = new LinkedList<>();

	public List<List<Tick>> getTicks() {
		return List.of(ticks1, ticks2, ticks3, ticks4);
	}

	Stream<NoteDataBytes[]> getNoteDataBytesStream() {
		return Stream.of(
				toNoteDataBytes(ticks1),
				toNoteDataBytes(ticks2),
				toNoteDataBytes(ticks3),
				toNoteDataBytes(ticks4));
	}

	private NoteDataBytes[] toNoteDataBytes(List<Tick> ticks) {
		return ticks.stream()
				.map(tick -> new NoteDataBytes(tick.getInstrument().value, tick.getVelocity()))
				.toArray(NoteDataBytes[]::new);
	}

	public void setTick(Tick tick, int tickIndex) {
		List<Tick> ticks = getTicks(tickIndex);
		ticks.add(tick);
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
