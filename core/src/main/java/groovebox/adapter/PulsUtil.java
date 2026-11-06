package groovebox.adapter;

class PulsUtil {
	private static final int RESOLUTION = 4; // TODO this is not a constant. It is linked to groovebox.service.Beat#ticksPerNote
	private static final int PULS_FACTOR = 120;
	static int swing = 20;

	static int getResolution() {
		return RESOLUTION * PULS_FACTOR;
	}

	static int toPuls(long tick) {
		int i = (int) tick * PULS_FACTOR;
		if (i % (getResolution() / 2) == 0 && i % getResolution() != 0) return i + swing;
		return i;
	}

	static long toTick(long puls) {
		return puls / PULS_FACTOR;
	}
}
