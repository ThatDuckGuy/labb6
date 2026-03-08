package labb6.carsim;

import labb6.generalsim.Event;
import labb6.generalsim.State;

/**
 * Ett event som stoppar simuleringen vid en given tid.
 * När händelsen sker stoppas simuleringens main loop.
 *
 * @author Hugo Igelström
 */
public class StopEvent extends Event {

    /**
     * Skapar ett StopEvent vid en given tid.
     *
     * @param time tidpunkten då simuleringen ska stoppas
     */
    public StopEvent(double time) {
        super(time);
    }

    /**
     * Stoppar simuleringen genom att sätta stopflaggan i tillståndet.
     *
     * @param s simuleringens nuvarande tillstånd (Castas till CarWashState)
     */
    @Override
    public void occur(State s) {
		CarWashState state = (CarWashState) s; // cast för hålla allmänna simulatorn separat

        state.updateSimView();
        state.stop();
    }
}
