package groovebox.model;

import java.util.function.Supplier;

public enum SampleBeatFactory {
	REGGAETON(() -> {
		Beat beat = new Beat();
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 0, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 1, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 2, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 3, 0);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 1, 2);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 3, 2);
		beat.defineTick(Instrument.SIDE_STICK, 1, 2);
		beat.defineTick(Instrument.SIDE_STICK, 3, 2);
		beat.defineTick(Instrument.LOW_FLOOR_TOM, 0, 3);
		beat.defineTick(Instrument.LOW_FLOOR_TOM, 2, 3);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 1, 1);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 2, 1);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 3, 1);
		beat.defineTick(Instrument.CRASH_CYMBAL_1,  0, 0);
		beat.setTempoInBPM(94.0f);
		return beat;
	}),
	CLASSIC_HOUSE(() -> {
		Beat beat = new Beat();
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 0, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 1, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 2, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 3, 0);
		beat.defineTick(Instrument.ACOUSTIC_SNARE, 1, 0);
		beat.defineTick(Instrument.ACOUSTIC_SNARE, 3, 0);
		beat.defineTick(Instrument.HAND_CLAP, 1, 0);
		beat.defineTick(Instrument.HAND_CLAP, 3, 0);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 0, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 1, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 2, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 3, 2);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  0, 1);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  0, 3);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  1, 1);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  1, 3);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  2, 1);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  2, 3);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  3, 1);
		beat.defineTick(Instrument.PEDAL_HI_HAT,  3, 3);
		beat.defineTick(Instrument.CRASH_CYMBAL_1,  0, 0);
		beat.defineTick(Instrument.SIDE_STICK, 1, 1);
		beat.defineTick(Instrument.SIDE_STICK, 3, 1);
		beat.setTempoInBPM(128.0f);
		return beat;
	}),
	MELBOURNE_BOUNCE(() -> {
		Beat beat = new Beat();
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 0, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 1, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 2, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 3, 0);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 1, 0);
		beat.defineTick(Instrument.ELECTRIC_SNARE, 3, 0);
		beat.defineTick(Instrument.HAND_CLAP, 1, 0);
		beat.defineTick(Instrument.HAND_CLAP, 3, 0);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 0, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 1, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 2, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 3, 2);
		beat.defineTick(Instrument.OPEN_HI_HAT, 0, 3);
		beat.defineTick(Instrument.OPEN_HI_HAT, 1, 3);
		beat.defineTick(Instrument.OPEN_HI_HAT, 2, 3);
		beat.defineTick(Instrument.OPEN_HI_HAT, 3, 3);
		beat.defineTick(Instrument.LOW_TOM, 1, 2);
		beat.defineTick(Instrument.LOW_TOM, 3, 2);
		beat.defineTick(Instrument.BASS_DRUM_1, 0, 0);
		beat.defineTick(Instrument.BASS_DRUM_1, 1, 0);
		beat.defineTick(Instrument.BASS_DRUM_1, 2, 0);
		beat.defineTick(Instrument.BASS_DRUM_1, 3, 0);
		beat.defineTick(Instrument.LOW_FLOOR_TOM, 0, 2);
		beat.defineTick(Instrument.LOW_FLOOR_TOM, 1, 2);
		beat.defineTick(Instrument.LOW_FLOOR_TOM, 2, 2);
		beat.defineTick(Instrument.LOW_FLOOR_TOM, 3, 2);
		beat.defineTick(Instrument.CRASH_CYMBAL_1,  0, 0);
		beat.setTempoInBPM(135.0f);
		return beat;
	}),
	HIP_HOP(() -> {
		Beat beat = new Beat();
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 0, 0);
		beat.defineTick(Instrument.ACOUSTIC_BASS_DRUM, 2, 2);
		beat.defineTick(Instrument.ACOUSTIC_SNARE, 1, 0);
		beat.defineTick(Instrument.ACOUSTIC_SNARE, 3, 0);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 0, 0);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 0, 1);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 0, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 0, 3);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 1, 0);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 1, 1);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 1, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 1, 3);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 2, 0);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 2, 1);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 2, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 2, 3);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 3, 0);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 3, 1);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 3, 2);
		beat.defineTick(Instrument.CLOSED_HI_HAT, 3, 3);
		beat.defineTick(Instrument.HAND_CLAP, 1, 0);
		beat.defineTick(Instrument.HAND_CLAP, 3, 0);
		beat.defineTick(Instrument.LOW_TOM, 1, 2);
		beat.defineTick(Instrument.LOW_TOM, 3, 2);
		beat.defineTick(Instrument.CRASH_CYMBAL_1, 0, 0);
		beat.setTempoInBPM(85.0f);
		return beat;
	}),
	;
	private final Supplier<Beat> beatSupplier;

	SampleBeatFactory(Supplier<Beat> beatSupplier) {
		this.beatSupplier = beatSupplier;
	}

	public Beat createBeat() {
		return beatSupplier.get();
	}
}
