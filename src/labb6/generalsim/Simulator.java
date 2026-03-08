package labb6.generalsim;

import java.util.PriorityQueue;

/**
 * En diskret simulator.
 * Hanterar en eventQueue och ser till att händelser inträffar.
 * Klassen är helt oberoende av vad som simuleras.
 *
 * @author Samatchaya Onpad
 */
public class Simulator implements EventQueue {

    private final State state;
    private final PriorityQueue<Event> eventQueue;

    /**
     * Skapar en ny simulator med ett givet state.
     *
     * @param state simuleringens tillstånd
     */
    public Simulator(State state) {
        this.state = state;
        this.eventQueue = new PriorityQueue<>();
    }

    /**
     * Lägger till ett event i queuen.
     *
     * @param e eventet som ska läggas till
     */
    public void addEvent(Event e) {
        eventQueue.add(e);
    }

    /**
     * Startar simuleringen och kör eventen i kronologisk ordning
     * tills händelsekön är tom eller simuleringen stoppas.
     */
    public void run() {
        while (!eventQueue.isEmpty() && state.isRunning()) {
            Event nextEvent = eventQueue.poll();
            state.setCurrentTime(nextEvent.getTime());
            nextEvent.occur(state);
        }
    }
}