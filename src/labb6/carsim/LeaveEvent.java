package labb6.carsim;

import labb6.generalsim.Event;
import labb6.generalsim.Simulator;
import labb6.generalsim.State;

public class LeaveEvent extends Event {

    private final Car car;
    private final boolean isFastWash;
    private final Simulator simulator;
    private final WashTime washTime;

    public LeaveEvent(double time, Car car, boolean isFastWash, Simulator simulator, WashTime washTime) {
        super(time);
        this.car = car;
        this.isFastWash = isFastWash;
        this.simulator = simulator;
        this.washTime = washTime;
    }

    @Override
    public void occur(State s) {
        CarWashState state = (CarWashState) s;
        state.updateStatistics();

        if (isFastWash) {
            state.freeUpFastWash();
        } else {
            state.freeUpSlowWash();
        }

        serveNextInQueue(state);
        state.update();
    }

    private void serveNextInQueue(CarWashState state) {
        if (!state.carQueueIsEmpty()) {
            Car nextCar = state.RemoveCar();
            double leaveTime;
            if (isFastWash) {
                leaveTime = washTime.nextFast(state.getCurrentTime());
                state.occupyFastWash();
            } else {
                leaveTime = washTime.nextSlow(state.getCurrentTime());
                state.occupySlowWash();
            }
            simulator.addEvent(new LeaveEvent(leaveTime, nextCar, isFastWash, simulator, washTime));
        }
    }
}