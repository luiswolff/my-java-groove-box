package groovebox.service;

public interface InstrumentDataApi {
	boolean isActive();
	void setActive(boolean active);
	int getVelocity();
	void setVelocity(int velocity);
}
