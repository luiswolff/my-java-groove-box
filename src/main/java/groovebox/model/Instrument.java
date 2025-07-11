package groovebox.model;

public enum Instrument {
	ACOUSTIC_BASS_DRUM(35),
	ELECTRIC_SNARE(40);
	public final int value;

	Instrument(int value) {
		this.value = value;
	}
}
