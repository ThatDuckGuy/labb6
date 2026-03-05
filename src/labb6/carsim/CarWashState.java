package labb6.carsim;

import java.util.Queue;
import java.util.LinkedList;
import labb6.generalsim.State;

public class CarWashState extends State {
	private final int fastWash, slowWash; // Amounts of carwashes
	private int freeFastWash, freeSlowWash;
	private final int queueLength;
	private final int seed;
	private final double[] slowWashTime, fastWashTime; // The time interval it can takes to wash a car
	private final double carInterval;
	private Queue<Car> carQueue;
	private CarFactory carFactory;

	public CarWashState(int fastWash, int slowWash, int queueLength, double[] slowWashTime, double[] fastWashTime,
			double carInterval, int seed) {
		this.fastWash = fastWash;
		this.slowWash = slowWash;
		this.freeFastWash = fastWash;
		this.freeSlowWash = slowWash;
		this.queueLength = queueLength;
		this.slowWashTime = slowWashTime;
		this.fastWashTime = fastWashTime;
		this.carInterval = carInterval;
		this.seed = seed;
		carQueue = new LinkedList<Car>();
		carFactory = new CarFactory();
	}

	public boolean carQueueIsFull() {
		if (carQueue.size() < queueLength) {
			return false;
		}
		return true;
	}

	public boolean carQueueIsEmpty() {
		return carQueue.isEmpty();
	}

	private Car createCar() {
		return carFactory.createCar(getCurrentTime());
	}

	private void addCar() {
		// Adds a car to carQueue if there is space
		if (!(carQueueIsFull())) {
			carQueue.add(createCar());
		}
	}

	public Car getFirstCar() {
		return carQueue.peek();
	}
	
	public Car getRemoveCar() {
		return carQueue.remove();
	}

	public double[] getFastWashTime() {
		return fastWashTime;
	}

	public double[] getSlowWashTime() {
		return slowWashTime;
	}

	public int getFreeFastWash() {
		return freeFastWash;
	}

	public int getFreeSlowWash() {
		return freeSlowWash;
	}

	private void occupyFastWash() {
		if (getFreeFastWash() != 0) {
			freeFastWash--;
		}
	}

	private void occupySlowWash() {
		if (getFreeSlowWash() != 0) {
			freeSlowWash--;
		}
	}

	private void freeUpFastWash() {
		if (getFreeFastWash() < fastWash) {
			freeFastWash++;
		}
	}

	private void freeUpSlowWash() {
		if (getFreeSlowWash() < slowWash) {
			freeSlowWash++;
		}
	}

	public double getCarInterval() {
		return carInterval;
	}

	public int getSeed() {
		return seed;
	}
	// NEXT HOW WILL CARWASH VIEW GET ACCESS TO THE CHANGES THE EVENT DOES. HOW WILL IT GET AVERAGE WAITING TIME??? 
}
