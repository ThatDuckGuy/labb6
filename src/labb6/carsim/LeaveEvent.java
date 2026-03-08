package labb6.carsim;

import labb6.generalsim.EventQueue;
import labb6.generalsim.State;

/**
 * Ett event som representerar att en bil lämnar biltvätten.
 * Om det finns bilar i kön tilldelas nästa bil direkt till den frigjorda maskinen,
 * annars blir maskinen ledig.
 *
 * @author Hugo Igelström
 */
public class LeaveEvent extends CarWashEvent {

    private final Car car;
    private final boolean isFastWash;

    /**
     * Skapar ett LeaveEvent vid en given tid.
     *
     * @param time       tiden då bilen lämnar biltvätten
     * @param car        bilen som lämnat
     * @param isFastWash sann om bilen tvättades i en snabb maskin
     * @param eventQueue simulatorn som events sker i
     * @param washTime   används för att beräkna tiden för olika events
     */
    public LeaveEvent(double time, Car car, boolean isFastWash, EventQueue eventQueue, WashTime washTime) {
        super(time, eventQueue, washTime);
        this.isFastWash = isFastWash;
        this.car = car;
    }

    /**
     * Effekten som LeaveEvent har på state.
     *
     * @param s simuleringens nuvarande tillstånd (castas till CarWashState)
     */
    @Override
    public void occur(State s) {
        CarWashState state = (CarWashState) s;
        state.updateStatistics();
        serveOrFree(state);
        state.update("Leave");
    }

    /**
     * Om det finns bilar i kön tilldelas nästa bil direkt till den frigjorda maskinen,
     * annars blir maskinen ledig.
     *
     * @param state simuleringens nuvarande tillstånd (efter cast)
     */
    private void serveOrFree(CarWashState state) {
        if (!state.carQueueIsEmpty()) {
            assignCarToMachine(state, state.RemoveCar(), isFastWash);
        } else {
            freeMachine(state);
        }
    }

    /**
     * Markerar maskinen som ledig.
     *
     * @param state simuleringens nuvarande tillstånd (efter cast)
     */
    private void freeMachine(CarWashState state) {
        if (isFastWash) {
            state.freeUpFastWash();
        } else {
            state.freeUpSlowWash();
        }
    }
}