package labb6.generalsim;

import java.util.Observable;
import java.util.ArrayList;

/**
 * En abstrakt klass som nvänds för att beskriva tillståndet i en simulering.
 * Klassen är en observable och kommer den att informera observerare då
 * någonting förändras. Klassen kommer att förändras genom events.
 */
public abstract class State extends Observable {
	private boolean running;
	private double currentTime;

	/**
	 * Skapar ett state för en simulation. Sätter start tiden till 0.
	 */
	public State() {
		running = true;
		currentTime = 0.0;
	}

	/**
	 * Returnerar ett booleskt värde som representerar ifall simulationen är igång.
	 * 
	 * @return ifall simulationen är på eller av.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Stoppar simulationen.
	 */
	public void stop() {
		running = false;
	}

	/**
	 * Medelar observerare då något förändrats i state.
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Medelar observerare då något förändrats i state och skickar med en parameter
	 * med relevant data.
	 * 
	 * @param arg information relevant för simView.
	 */
	public void update(Object arg) {
		setChanged();
		notifyObservers(arg);
	}

	/**
	 * Returnerar den nuvarande tiden i state
	 * 
	 * @return nuvarande titen i simulationen
	 */
	public double getCurrentTime() {
		return currentTime;
	}

	/**
	 * Sätter tiden i state till ett nytt värde angivet av timeTo
	 * 
	 * @param TimeTo den tid som den nuvarande tiden ska sättas till
	 */
	public void setCurrentTime(double timeTo) {
		currentTime = timeTo;
	}

}
