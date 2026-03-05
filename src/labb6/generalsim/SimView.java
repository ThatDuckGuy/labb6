package labb6.generalsim;

import java.util.Observable;
import java.util.Observer;

public abstract class SimView implements Observer{
	private State state;
	
	
	public  SimView(State state) {
		this.state = state;
		state.addObserver(this);
	}
	
	public abstract void update(Observable o, Object args);
		
	public abstract void output();
	
}
