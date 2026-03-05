package labb6.generalsim;

import java.util.Observable;
import java.util.ArrayList;

public abstract class State extends Observable {
	private boolean running;
	private double currentTime;
	
	public State() {
		running = true;
		currentTime = 0.0;
	}

	public boolean isRunning() {
		return running;
	}

	public void stop() {
		running = false;
	}

	public void updateSimView() {
		setChanged();
		notifyObservers();
	}

	public double getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(double TimeTo) {
		currentTime = TimeTo;
	}

}
