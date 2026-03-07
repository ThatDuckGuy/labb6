package labb6.generalsim;

import java.util.Observable;
import java.util.Observer;

/**
 * En abstrakt klassen är en observer som observerar ett object av typen state. Klassens
 * används för att visualisera förändringar i state.
 * 
 */
public abstract class SimView implements Observer {
	private State state;

	/**
	 * Skapar en vy och kopplar den till ett givet state
	 * som ska observeras.
	 * 
	 * @param state staten som vyn ska observera. 
	 */
	public SimView(State state) {
		this.state = state;
		state.addObserver(this);
	}
	
	/**
	 * Kallas då en förändring sker i state och ska 
	 * presentera relevant information vid förändringen.
	 */
	public abstract void update(Observable o, Object args);


}
