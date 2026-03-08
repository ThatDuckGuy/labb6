package labb6.carsim;

import java.util.Observable;
import java.util.Formatter;
import labb6.generalsim.SimView;
import labb6.generalsim.State;

public class CarSimView extends SimView {
	private CarWashState state;

	public static void main(String[] args) {
		System.out.println("----------------------------");
		System.out.format("%-25s %-4.2f%n" , "Total idle machine time: ", 1.11);
		System.out.format("%-25s %-4.2f%n" , "Total queueing time: ", 1.11);
		System.out.format("%-25s %-4.2f%n" , "Mean queueing time: ", 1.11);
		System.out.format("%-25s %-4d%n" , "Rejected cars: ", 5);
	}

	public CarSimView(CarWashState state) {
		super(state);
		this.state = state;
	}

	private void printBegining() {
		System.out.println("Fast machine: " + state.getTotalFastWash());
		System.out.println("Slow machine: " + state.getTotalSlowWash());
		System.out.println("Fast distribution: " + state.getFastWashTime());
		System.out.println("Slow distribution: " + state.getSlowWashTime());
		System.out.println("Exponential distrobution with lambda = " + state.getCarInterval());
		System.out.println("Seed = " + state.getSeed());
		System.out.println("Max queue size: " + state.getQueueLength());
		System.out.println("------------------------------------------");
		System.out.format("%-10s %-8s %-4s %-6s %-6s %-10s %-11s %-8s%n", "Time", "Event", "Id", "Fast", "Slow",
				"IdleTime", "QueueSize", "Rejected");
	}

	private void printEvent(String eventType) {
		System.out.format("%-10.2f %-8s %-4d %-6d %-6.2d %-10.2f %-11d %-8d%n", state.getCurrentTime(), eventType,
				state.getCurrentCarId(), state.getFreeFastWash(), state.getFreeSlowWash(), state.getIdleTime(),
				state.getCurrentQueueLength(), state.getRejectedCars());
	}
	
	private void printStatistics() {
		System.out.println("----------------------------");
		System.out.format("%-25s %-4.2f%n" , "Total idle machine time: ", state.getIdleTime());
		System.out.format("%-25s %-4.2f%n" , "Total queueing time: ", state.getQueueTime());
		System.out.format("%-25s %-4.2f%n" , "Total mean queueing time: ", state.getMeanQueuTime());
		System.out.format("%-25s %-4d%n" , "Rejected cars: ", state.getRejectedCars());
	}
	@Override
	public void update(Observable o, Object arg) {
		String currentEvent = (String) arg;
		
		switch (currentEvent) {
		case "Start":
			printBegining();
			break;
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
		}
	}
}
