package labb6.carsim;

import labb6.generalsim.Event;
import labb6.generalsim.Simulator;
import labb6.generalsim.State;

public class ArriveEvent extends Event {

	 private final Simulator simulator;
	    private final WashTime washTime;

	    public ArriveEvent(double time, Simulator simulator, WashTime washTime) {
	        super(time);
	        this.simulator = simulator;
	        this.washTime = washTime;
	    }

	@Override
	public void occur(State s) {
		CarWashState state = (CarWashState) s; // cast för hålla allmänna simulatorn separat
		state.setCurrentTime(getTime());
		state.updateStatistics();
    

		Car car = state.createCar();
		state.setCurrentCarId(car);
		state.update("Arrive");
		washOrQueue(state, car);
		

		nextArrival(state);

	}

	private void washOrQueue(CarWashState state, Car car) {
		
		if (state.getFreeFastWash() > 0) {
			washCar(state, car, true);
		} else if (state.getFreeSlowWash() > 0) {
			washCar(state, car, false);
		} else {
			state.addCar(car);
		}
	}

	private void washCar(CarWashState state, Car car, boolean isFastWash) {
		double leaveTime;
        if (isFastWash) {
            leaveTime = washTime.nextFast(state.getCurrentTime());
            state.occupyFastWash();
        } else {
            leaveTime = washTime.nextSlow(state.getCurrentTime());
            state.occupySlowWash();
        }

        simulator.addEvent(new LeaveEvent(leaveTime, car, isFastWash, simulator, washTime));
    }


    private void nextArrival(CarWashState state) {
        simulator.addEvent(new ArriveEvent(washTime.nextArrival(state.getCurrentTime()),
                simulator, washTime));
    }
}