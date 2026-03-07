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
    }

    private void washOrQueue(CarWashState state, Car car) {
        if (state.getFreeFastWash() > 0) {
            state.occupyFastWash();
        } else if (state.getFreeSlowWash() > 0) {
            state.occupySlowWash();
        } else {
            state.addCar();
        }
    }
}