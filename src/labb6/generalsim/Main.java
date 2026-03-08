package labb6.generalsim;

import labb6.carsim.*;
import labb6.*;

/**
 * Startar en biltvätts simulator. Simulationen startas med ett arrive event.
 */
public class Main {

	public static void main(String[] args) {
		int seed = 1234;
		double lambda = 2.0;
		double[] fastRange = { 2.8, 4.6 };
		double[] slowRange = { 3.5, 6.7 };

		ExponentialRandomStream arrivalStream = new ExponentialRandomStream(lambda, seed);
		UniformRandomStream fastWashTime = new UniformRandomStream(fastRange[0], fastRange[1], seed);
		UniformRandomStream slowWashTime = new UniformRandomStream(slowRange[0], slowRange[1], seed);

		WashTime washTime = new WashTime(arrivalStream, fastWashTime, slowWashTime);

		CarWashState state = new CarWashState(2, 2, 5, slowRange, fastRange, lambda, seed);
		CarSimView view = new CarSimView(state);
		Simulator sim = new Simulator(state);

		sim.addEvent(new ArriveEvent(washTime.nextArrival(0.0), sim, washTime));
		sim.addEvent(new StopEvent(15.0));

		sim.run();
	}
}
