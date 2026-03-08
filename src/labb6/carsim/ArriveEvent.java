package labb6.carsim;

import labb6.generalsim.EventQueue;
import labb6.generalsim.State;

/**
 * Ett event som representerar att en bil anländer till biltvätten.
 * Om det finns en ledig maskin skickas bilen dit, annars ställs den i kön.
 * Efter ankomsten skapas nästa ArriveEvent.
 *
 * @author Hugo Igelström
 */
public class ArriveEvent extends CarWashEvent {

    /**
     * Skapar ett ArriveEvent vid en given tid.
     *
     * @param time       tiden då bilen anländer
     * @param eventQueue simulatorn som events sker i
     * @param washTime   används för att beräkna tiden för olika events
     */
    public ArriveEvent(double time, EventQueue eventQueue, WashTime washTime) {
        super(time, eventQueue, washTime);
    }

    /**
     * Effekten som ArriveEvent har på state.
     *
     * @param s simuleringens nuvarande tillstånd (castas som CarWashState).
     */
    @Override
    public void occur(State s) {
        CarWashState state = (CarWashState) s; // cast för hålla allmänna simulatorn separat
        state.setCurrentTime(getTime());
        state.updateStatistics();

        Car car = state.createCar();
        state.setCurrentCarId(car);
        state.update("Arrive");
        serveOrQueue(state, car);

        nextArrival(state);
    }

    /**
     * Skickar bilen till en ledig maskin om det finns,
     * annars läggs bilen till i kön eller avvisas.
     *
     * @param state simuleringens nuvarande tillstånd (efter cast)
     * @param car   bilen som anlänt
     */
    private void serveOrQueue(CarWashState state, Car car) {
        if (state.getFreeFastWash() > 0) {
            assignCarToMachine(state, car, true);
        } else if (state.getFreeSlowWash() > 0) {
            assignCarToMachine(state, car, false);
        } else {
            state.addCar(car);
        }
    }

    /**
     * Skapar nästa ArriveEvent.
     *
     * @param state simuleringens nuvarande tillstånd (efter cast)
     */
    private void nextArrival(CarWashState state) {
        Double arriveTime = washTime.nextArrival(state.getCurrentTime());
        ArriveEvent nextWash = new ArriveEvent(arriveTime, eventQueue, washTime);
        eventQueue.addEvent(nextWash);
    }
}