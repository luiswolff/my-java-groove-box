package groovebox.model;

import java.util.Objects;

public class Tick {
	private final Instrument instrument;
	private int velocity;

	public Tick(Instrument instrument, int velocity) {
		this.instrument = instrument;
		this.velocity = velocity;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

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
