package labb6;

import java.util.PriorityQueue;

/**
 * En generell diskret händelsestyrd simulator.
 * Denna klass är helt oberoende av vad som simuleras (t.ex. en biltvätt).
 */
public class Simulator {

    // Referens till tillståndet (State) som håller reda på tid och om simuleringen ska stoppa.
    private State state;

    // Händelsekön (Event Queue/EQ) som lagrar framtida händelser.
    // PriorityQueue ser till att händelserna alltid bearbetas i kronologisk ordning.
    private PriorityQueue<Event> eventQueue;

    /**
     * Konstruktor för simulatorn.
     * Genom att ta in ett State-objekt kan flera simuleringar köras oberoende av varandra.
     */
    public Simulator(State state) {
        this.state = state;
        this.eventQueue = new PriorityQueue<>();
    }

    /**
     * Lägger till en ny händelse i händelsekön.
     */
    public void addEvent(Event e) {
        eventQueue.add(e);
    }

    /**
     * Huvudloopen som kör simuleringen.
     * Den extraherar händelser en efter en och får dem att inträffa.
     */
    public void run() {
        // Simuleringen fortsätter så länge kön inte är tom och stopp-flaggan i State inte är satt.
        while (!eventQueue.isEmpty() && !state.isSimulationOver()) {
            
            // Hämtar nästa händelse (den med tidigast tidpunkt).
            Event nextEvent = eventQueue.poll();
            
            // Uppdaterar simulatorns virtuella tid till tidpunkten för den aktuella händelsen.
            state.setCurrentTime(nextEvent.getTime());
            
            // Kör händelsens logik (vilket kan ändra tillståndet eller skapa nya händelser).
            nextEvent.execute(this);
        }
    }
}