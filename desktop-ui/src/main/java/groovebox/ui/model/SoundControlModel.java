package groovebox.ui.model;

import java.util.LinkedList;
import java.util.Queue;

import groovebox.service.SoundControl;
import groovebox.service.TickPosition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Subscription;

public class SoundControlModel {
	private final ObjectProperty<Integer> tempo = new SimpleObjectProperty<>();
	private final BooleanProperty infinity = new SimpleBooleanProperty(false);
	private final ObjectProperty<Integer> loopCount = new SimpleObjectProperty<>();
	private final BooleanProperty running = new SimpleBooleanProperty();
	private final ObjectProperty<TickPosition> tickPosition = new SimpleObjectProperty<>();

	private final SoundControl soundControl;
	private final Queue<Subscription> soundControlSubscriptions = new LinkedList<>();

	public SoundControlModel(SoundControl soundControl) {
		this.soundControl = soundControl;
		reload();
	}

	public boolean nextTickPosition() {
		TickPosition rawTickPosition = soundControl.getTickPosition();
		tickPosition.set(rawTickPosition);
		return rawTickPosition != null;
	}

	public void reload() {
		unsubscribeAll();
		loadValues();
		createSubscriptions();
	}

	private void unsubscribeAll() {
		Subscription subscription;
		while ((subscription = soundControlSubscriptions.poll()) != null) {
			subscription.unsubscribe();
		}
	}

	private void loadValues() {
		tempo.setValue(soundControl.getTempoInBPM());
		infinity.setValue(soundControl.isLoopContinuously());
		loopCount.setValue(soundControl.getLoopCount());
		running.setValue(soundControl.isRunning());
	}

	private void createSubscriptions() {
		soundControlSubscriptions.add(tempo.subscribe(soundControl::setTempoInBPM));
		soundControlSubscriptions.add(infinity.subscribe(soundControl::setLoopContinuously));
		soundControlSubscriptions.add(loopCount.subscribe(soundControl::setLoopCount));
		soundControlSubscriptions.add(running.subscribe(soundControl::setRunning));
	}

	public ObjectProperty<Integer> tempoProperty() {
		return tempo;
	}

	public BooleanProperty infinityProperty() {
		return infinity;
	}

	public ObjectProperty<Integer> loopCountProperty() {
		return loopCount;
	}

	public BooleanProperty runningProperty() {
		return running;
	}

	public ObjectProperty<TickPosition> tickPositionProperty() {
		return tickPosition;
	}
}
