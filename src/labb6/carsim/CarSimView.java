package labb6.carsim;

import java.util.Observable;
import java.util.Arrays;
import java.util.Formatter;
import labb6.generalsim.SimView;
import labb6.generalsim.State;

/**
 * Vy för biltvättssimuleringen, tar förändringar i CarWashState och skriver ut
 * information om varje händelse samt slutstatistik.
 */
public class CarSimView extends SimView {
	private CarWashState state;
	private boolean firstUpdate;

	/**
	 * Skapar en vy och kopplar den till ett givet CarWashState.
	 *
	 * @param state tillståndet som vyn ska observera
	 */
	public CarSimView(CarWashState state) {
		super(state);
		this.state = state;
		this.firstUpdate = true;
	}

	/**
	 * Skriver ut simuleringens startkonfiguration, inklusive antal maskiner,
	 * tidsintervall, köparametrar och kolumnrubriker för event tabellen.
	 */
	private void printBegining() {
		System.out.println("Fast machine: " + state.getTotalFastWash());
		System.out.println("Slow machine: " + state.getTotalSlowWash());
		System.out.println("Fast distribution: " + Arrays.toString(state.getFastWashTime()));
		System.out.println("Slow distribution: " + Arrays.toString(state.getSlowWashTime()));
		System.out.println("Exponential distrobution with lambda = " + state.getCarInterval());
		System.out.println("Seed = " + state.getSeed());
		System.out.println("Max queue size: " + state.getQueueLength());
		System.out.println("------------------------------------------");
		System.out.format("%-10s %-8s %-4s %-6s %-6s %-10s %-10s %-11s %-8s%n", "Time", "Event", "Id", "Fast", "Slow",
				"IdleTime", "QueueTime", "QueueSize", "Rejected");
	}

	/**
	 * Skriver ut information om den aktuella händelsen, inklusive tid, händelsetyp,
	 * bil-id, antal lediga maskiner, ackumulerad ledig maskintid, köstorlek och
	 * antal avvisade bilar.
	 *
	 * @param eventType typen av händelse som ska skrivas ut, t.ex. "Arrive" eller
	 *                  "Leave"
	 */
	private void printEvent(String eventType) {
		if (eventType.equals("Stop")) {
			System.out.format("%-10.2f %-8s %-4s %-6d %-6d %-10.2f %-10.2f %-11d %-8d%n", state.getCurrentTime(),
					eventType, "", state.getFreeFastWash(), state.getFreeSlowWash(), state.getIdleTime(),
					state.getQueueTime(), state.getCurrentQueueLength(), state.getRejectedCars());
		} else {
			System.out.format("%-10.2f %-8s %-4d %-6d %-6d %-10.2f %-10.2f %-11d %-8d%n", state.getCurrentTime(),
					eventType, state.getCurrentCarId(), state.getFreeFastWash(), state.getFreeSlowWash(),
					state.getIdleTime(), state.getQueueTime(), state.getCurrentQueueLength(), state.getRejectedCars());
		}
	}

	/**
	 * Skriver ut slutstatistik efter simuleringen, inklusive total ledig maskintid,
	 * total kötid, medel kötid och antal avvisade bilar.
	 */
	private void printStatistics() {
		System.out.println("----------------------------");
		System.out.format("%-25s %-4.2f%n", "Total idle machine time: ", state.getIdleTime());
		System.out.format("%-25s %-4.2f%n", "Total queueing time: ", state.getQueueTime());
		System.out.format("%-25s %-4.2f%n", "Mean queueing time: ", state.getMeanQueuTime());
		System.out.format("%-25s %-4d%n", "Rejected cars: ", state.getRejectedCars());
	}

	/**
	 * Kallas då tillståndet förändras och skriver ut relevant information beroende
	 * på händelsetyp. Vid "Start" skrivs konfigurationen ut, vid "Arrive" och
	 * "Leave" skrivs händelseraden ut, och vid "Stop" skrivs händelseraden följt av
	 * slutstatistik ut.
	 *
	 * @param o   det observerade objektet
	 * @param arg händelsetypen som en sträng: "Start", "Arrive", "Leave" eller
	 *            "Stop"
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (firstUpdate) {
			printBegining();
			firstUpdate = false;
		}

		if (!(arg instanceof String)) {
			throw new IllegalArgumentException("Måste skicka ett event namn som argument.");
		}

		String currentEvent = (String) arg;
		switch (currentEvent) {
		case "Arrive":
			printEvent(currentEvent);
			break;
		case "Leave":
			printEvent(currentEvent);
			break;
		case "Stop":
			printEvent(currentEvent);
			printStatistics();
			break;
		default:
			throw new IllegalArgumentException("Not a valid event");
		}
	}
}
