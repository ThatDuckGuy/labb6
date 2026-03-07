package labb6.carsim;

//Ärvs så den kan tala om när simulatorn ska stängas
public class StopEvent extends Event{
	
	public StopEvent(double time) {
		
		super(time); //skickar tidpunk. till Event så prioq kan sortera på rätt plats
	}
    
}
