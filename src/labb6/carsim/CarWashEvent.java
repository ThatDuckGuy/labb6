package labb6.carsim;

import labb6.generalsim.Event;
import labb6.generalsim.Simulator;

/**
 * Abstrakt klass som representerar ett event relaterat till biltvätten.
 * Innehåller logik för som delas av {@code ArriveEvent} och {@code LeaveEvent}.
 *
 * @author Hugo Igelström
 */
public abstract class CarWashEvent extends Event {

    protected final Simulator simulator;
    protected final WashTime washTime;

    /**
     * Konstruktor för CarWashEvent. Används för att skapa
     * gemensamma fält, och för att behålla arvshirarkin.
     *
     * @param time      tiden då händelsen inträffar
     * @param simulator simulatorn som events läggs till i
     * @param washTime  används för att beräkna tiden för olika events
     */
    public CarWashEvent(double time, Simulator simulator, WashTime washTime) {
        super(time);
        this.simulator = simulator;
        this.washTime = washTime;
    }

    /**
     * Skickar en bil till en tvättmaskin och skapar ett
     * {@code LeaveEvent} för när bilen är färdigtvättad.
     *
     * @param state      simuleringens nuvarande tillstånd
     * @param car        bilen som ska tvättas
     * @param isFastWash sann om bilen ska tvättas i en snabb maskin
     */
    protected void assignCarToMachine(CarWashState state, Car car, boolean isFastWash) {
        double leaveTime;
        if (isFastWash) {
            leaveTime = washTime.nextFast(state.getCurrentTime());
            state.occupyFastWash();
        } else {
            leaveTime = washTime.nextSlow(state.getCurrentTime());
            state.occupySlowWash();
        }
        LeaveEvent nextLeaveEvent = new LeaveEvent(leaveTime, car, isFastWash, simulator, washTime);
        simulator.addEvent(nextLeaveEvent);
    }
}