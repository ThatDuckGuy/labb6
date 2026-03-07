package labb6.generalsim;

import java.util.PriorityQueue;

public class Simulator {

    private final State state;
    private final PriorityQueue<Event> eventQueue;

    public Simulator(State state) {
        this.state = state;
        this.eventQueue = new PriorityQueue<>();
    }

    public void addEvent(Event e) {
        eventQueue.add(e);
    }

    public void run() {
        while (!eventQueue.isEmpty() && state.isRunning()) {
            Event nextEvent = eventQueue.poll();
            state.setCurrentTime(nextEvent.getTime());
            nextEvent.occur(state);
        }
    }
}