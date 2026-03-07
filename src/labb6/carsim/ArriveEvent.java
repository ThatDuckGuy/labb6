package labb6.carsim;

import labb6.ExponentialRandomStream;
import labb6.UniformRandomStream;
import labb6.generalsim.Event;
import labb6.generalsim.Simulator;
import labb6.generalsim.State;

public class ArriveEvent extends Event {

    private final Simulator simulator;
    private final ExponentialRandomStream arrivalStream;
    private final UniformRandomStream fastWashStream;
    private final UniformRandomStream slowWashStream;

    public ArriveEvent(double time, Simulator simulator,
            ExponentialRandomStream arrivalStream,
            UniformRandomStream fastWashStream,
            UniformRandomStream slowWashStream) {
        super(time);
        this.simulator = simulator;
        this.arrivalStream = arrivalStream;
        this.fastWashStream = fastWashStream;
        this.slowWashStream = slowWashStream;
    }

    @Override
    public void occur(State s) {
        CarWashState state = (CarWashState) s; // cast för hålla allmänna simulatorn separat
        state.updateStatistics();

        Car car = state.createCar();
        washOrQueue(state, car);
        nextArrival(state);
        state.update();
    }

    private void washOrQueue(CarWashState state, Car car) {
        if (state.getFreeFastWash() > 0) {
            washCar(state, car, true);
        } else if (state.getFreeSlowWash() > 0) {
            washCar(state, car, false);
        } else {
            state.addCar();
        }
    }

    private void washCar(CarWashState state, Car car, boolean isFastWash) {
        double leaveTime;

        if (isFastWash) {
            leaveTime = nextFastTime(state);
            state.occupyFastWash();
        } else {
            leaveTime = nextSlowTime(state);
            state.occupySlowWash();
        }

        simulator.addEvent(new LeaveEvent(leaveTime, car, isFastWash, simulator));
    }

    private void nextArrival(CarWashState state) {
        simulator.addEvent(
                new ArriveEvent(nextArrivalTime(state), simulator, arrivalStream, fastWashStream, slowWashStream));
    }

    private double nextArrivalTime(CarWashState state) {
        return state.getCurrentTime() + arrivalStream.next();
    }

    private double nextFastTime(CarWashState state) {
        return state.getCurrentTime() + fastWashStream.next();
    }

    private double nextSlowTime(CarWashState state) {
        return state.getCurrentTime() + slowWashStream.next();
    }
}