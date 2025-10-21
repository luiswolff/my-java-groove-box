package groovebox.ui.timer;

import groovebox.ui.model.SoundControlModel;
import javafx.animation.AnimationTimer;

public class TickPositionTimer implements AutoCloseable {
	private final SoundControlModel soundControlModel;
	private final MyAnimationTimer animationTimer = new MyAnimationTimer();

	public TickPositionTimer(SoundControlModel soundControlModel) {
		this.soundControlModel = soundControlModel;
		init();
	}

	private void init() {
		soundControlModel.runningProperty().subscribe(running -> {
			if (Boolean.TRUE.equals(running)) {
				animationTimer.start();
			}
		});
	}

	@Override
	public void close() {
		animationTimer.stop();
	}

	private class MyAnimationTimer extends AnimationTimer {
		@Override
		public void handle(long now) {
			if (!soundControlModel.nextTickPosition()) {
				animationTimer.stop();
				soundControlModel.reload();
			}
		}
	}
}
