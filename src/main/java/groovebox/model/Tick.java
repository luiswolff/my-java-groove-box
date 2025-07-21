package groovebox.model;

import java.util.Objects;

public record Tick(Instrument instrument, int velocity) {
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		Tick tick = (Tick) o;
		return instrument == tick.instrument;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(instrument);
	}
}
