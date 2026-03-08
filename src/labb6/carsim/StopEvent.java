package labb6.carsim;

import labb6.generalsim.Event;
import labb6.generalsim.State;

//Ärvs så den kan tala om när simulatorn ska stängas
public class StopEvent extends Event{
	
	public StopEvent(double time) {
		
		super(time); //skickar tidpunk. till Event så prioq kan sortera på rätt plats
	}
    
	@Override
    public void occur(State s) {
		CarWashState state = (CarWashState) s; // cast för hålla allmänna simulatorn separat
        
    }
}
