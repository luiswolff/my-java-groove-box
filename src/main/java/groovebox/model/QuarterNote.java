package groovebox.model;

public class QuarterNote {
	private Tick tick1;
	private Tick tick2;
	private Tick tick3;
	private Tick tick4;

	public QuarterNote(Tick tick1, Tick tick2, Tick tick3, Tick tick4) {
		this.tick1 = tick1;
		this.tick2 = tick2;
		this.tick3 = tick3;
		this.tick4 = tick4;
	}

	public Tick getTick1() {
		return tick1;
	}

	public void setTick1(Tick tick1) {
		this.tick1 = tick1;
	}

	public Tick getTick2() {
		return tick2;
	}

	public void setTick2(Tick tick2) {
		this.tick2 = tick2;
	}

	public Tick getTick3() {
		return tick3;
	}

	public void setTick3(Tick tick3) {
		this.tick3 = tick3;
	}

	public Tick getTick4() {
		return tick4;
	}

	public void setTick4(Tick tick4) {
		this.tick4 = tick4;
	}
}
