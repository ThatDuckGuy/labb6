// temporär implementation av claude för att testa arrive, kommer skriva om senare.

package labb6.carsim;

import labb6.generalsim.Event;
import labb6.generalsim.State;
import labb6.generalsim.Simulator;

public class LeaveEvent extends Event {

    private final Car car;
    private final boolean isFast;
    private final Simulator simulator;

    public LeaveEvent(double time, Car car, boolean isFast, Simulator simulator) {
        super(time);
        this.car = car;
        this.isFast = isFast;
        this.simulator = simulator;
    }

    @Override
    public void occur(State s) {
        CarWashState state = (CarWashState) s;

        if (isFast) {
            state.freeUpFastWash();
        } else {
            state.freeUpSlowWash();
        }

        //state.updateSimView();
    }
}
