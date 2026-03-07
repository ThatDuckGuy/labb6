package labb6.carsim;

import labb6.generalsim.Event;
import labb6.generalsim.State;

public class ArriveEvent extends Event{
    private final CarFactory carFactory;

    public ArriveEvent(double time, CarFactory carFactory) {
        super(time);
        this.carFactory = carFactory;
    }

    @Override
    public void occur(State s) {
        Car car = carFactory.createCar(s.getCurrentTime());
        CarWashState state = (CarWashState) s; // cast för hålla allmänna simulatorn separat

        // if (state.getFreeFastWash() > 0) {
        //     state.occupyFastWash();
        // }
    }
    
}
