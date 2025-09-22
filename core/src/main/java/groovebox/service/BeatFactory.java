package groovebox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeatFactory {
	private final int tempoInBPM;
	private final List<InstrumentPosition>  instrumentPositions;
	private BeatFactory(int tempoInBPM, List<InstrumentPosition> instrumentPositions) {
		this.tempoInBPM = tempoInBPM;
		this.instrumentPositions = instrumentPositions;
	}

	public void apply(SoundControl soundControl) {
		soundControl.setTempoInBPM(tempoInBPM);
	}

	public <T extends List<Phrase>> Beat<T> createBeat(Supplier<T> phrasesSupplier) {
		Beat<T> beat = new Beat<>(phrasesSupplier);
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
		private final List<InstrumentPosition> instrumentPositions = new ArrayList<>();
		private Builder() {}

		public Builder withTempoInBPM(int tempoInBPM) {
			this.tempoInBPM = tempoInBPM;
			return this;
		}

		public Builder withInstrumentPositions(Instrument instrument, int noteIndex, int tickIndex) {
			this.instrumentPositions.add(new InstrumentPosition(instrument, noteIndex, tickIndex));
			return this;
		}

		public BeatFactory build() {
			return new BeatFactory(tempoInBPM, instrumentPositions);
		}
	}
}
