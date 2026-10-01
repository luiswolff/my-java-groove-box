package groovebox.service;

import java.util.ArrayList;
import java.util.List;

public class BeatFactory {
	private final int tempoInBPM;
	private final List<InstrumentPosition>  instrumentPositions;
	private final int noteCount;
	private final int ticksPerNote;

	private BeatFactory(int tempoInBPM, int noteCount, int ticksPerNote, List<InstrumentPosition> instrumentPositions) {
		this.tempoInBPM = tempoInBPM;
		this.noteCount = noteCount;
		this.ticksPerNote = ticksPerNote;
		this.instrumentPositions = instrumentPositions;
	}

	public void apply(SoundControl soundControl) {
		soundControl.setTempoInBPM(tempoInBPM);
	}

	public Beat createBeat() {
		Beat beat = new Beat(noteCount, ticksPerNote);
		for (InstrumentPosition instrumentPosition : instrumentPositions) {
			InstrumentDataApi instrumentDataApi = beat.getPhrases().getFirst()
					.getNotes().get(instrumentPosition.noteIndex())
					.getTicks().get(instrumentPosition.tickIndex())
					.getInstrumentData(instrumentPosition.instrument());
			instrumentDataApi.setActive(true);
		}
		return beat;
	}

	private record InstrumentPosition(Instrument instrument, int noteIndex, int tickIndex) {}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private int tempoInBPM = 120;
		private int noteCount = 4;
		private int ticksPerNote = 4;
		private final List<InstrumentPosition> instrumentPositions = new ArrayList<>();
		private Builder() {}

		public Builder withTempoInBPM(int tempoInBPM) {
			this.tempoInBPM = tempoInBPM;
			return this;
		}

		public Builder withStepResolution(int noteCount, int ticksPerNote) {
			this.noteCount = noteCount;
			this.ticksPerNote = ticksPerNote;
			return this;
		}

		public Builder withInstrumentPositions(Instrument instrument, int noteIndex, int tickIndex) {
			this.instrumentPositions.add(new InstrumentPosition(instrument, noteIndex, tickIndex));
			return this;
		}

		public BeatFactory build() {
			return new BeatFactory(tempoInBPM, noteCount, ticksPerNote, instrumentPositions);
		}
	}
}
