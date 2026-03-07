package labb6.carsim;

import java.util.Queue;
import java.util.LinkedList;
import labb6.generalsim.State;

/**
 * Klassen ärver state och representerar tillståndet i en biltvätts simulering.
 * Tillståndet kan endast förändras genom events.
 * 
 */
public class CarWashState extends State {
	private final int fastWash, slowWash; // Amounts of carwashes
	private int freeFastWash, freeSlowWash;
	private final int queueLength;
	private final int seed;
	private final double[] slowWashTime, fastWashTime; // The time interval it can takes to wash a car
	private final double carInterval;
	private Queue<Car> carQueue;
	CarFactory carFactory;
	private double carwashIdleTime;
	private double totalQueueTime, meanQueueTime;
	private int rejectedCars;
	private double lastEventTime;

	/**
	 * Skapar ett nytt tillstånd för en biltvätts simulation och initierar värden
	 * som beskriver simulationen.
	 * 
	 * @param fastWash     mängden snabba biltvättar.
	 * @param slowWash     mängden långsamma bilträttar.
	 * @param queueLength  hur lång kön till biltvätten får bli.
	 * @param slowWashTime det undre och övre tids intervallet det kan ta för en bil
	 *                     att bli tvättad i en långsamm biltvätt.
	 * @param fastWashTime det övre och undre tids intervallet det kan ta för en bil
	 *                     att bli tvättad i en snabb biltvätt.
	 * @param carInterval  tids intervallet mellan två bilars ankomst
	 * @param seed         start värdet för en slumptalsgenerator
	 */
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
		this.carQueue = new LinkedList<Car>();
		this.carFactory = new CarFactory();
		this.totalQueueTime = 0.0;
		this.meanQueueTime = 0.0;
		this.rejectedCars = 0;
		this.lastEventTime = 0;
	}

	/**
	 * Kollar ifall kön till biltvätten är full.
	 * 
	 * @return sant eller falsk. Sant om kön är full. Falskt om kön inte är full.
	 * 
	 */
	public boolean carQueueIsFull() {
		if (carQueue.size() < queueLength) {
			return false;
		}
		return true;
	}

	/**
	 * Kollar ifall kön till biltvätten är tom.
	 * 
	 * @return sant eller falsk. Sant om kön är tom, annars falsk.
	 */
	public boolean carQueueIsEmpty() {
		return carQueue.isEmpty();
	}

	/**
	 * Skapar en bil.
	 * 
	 * @return en bil
	 */
	Car createCar() {
		return carFactory.createCar(getCurrentTime());
	}

	/**
	 * Lägger till en bil till kön ifall kön inte är full.
	 */
	void addCar() {
		// Adds a car to carQueue if there is space
		if (!(carQueueIsFull())) {
			carQueue.add(createCar());
		} else {
			rejectedCars++;
		}
	}

	/**
	 * Kollar vilken bil som är först i kön.
	 * 
	 * @return den bil som är först i kön.
	 * 
	 */
	public Car getFirstCar() { // Metoden behövs kaske inte
		if (!(carQueueIsEmpty())) {
			return carQueue.peek();
		}

		return null;

	}

	/**
	 * Tar bort och returnerar den bil som var först i kön. Samt ökar antalet
	 * servade bilar
	 * 
	 * @return den bil som var först i kön.
	 */
	public Car RemoveCar() {
		Car currentCar = carQueue.poll();

		if (currentCar != null) {
		
			return currentCar;
		}
		return null;
	}

	
	/**
	 * Beräknar mängden idle time per biltvätt som skapats mellan två event. Denna
	 * metod ska kallas först vid varje event.
	 */
	void updateStatistics() {
		double timeBetweenEvent = getCurrentTime() - lastEventTime;
		
		totalQueueTime += timeBetweenEvent * carQueue.size();
		if(carFactory.getNumberOfCars() > 0) {
			meanQueueTime = totalQueueTime / carFactory.getNumberOfCars();
		}
	
		
		int idleCarWashes = freeFastWash + freeSlowWash;

		carwashIdleTime += idleCarWashes * timeBetweenEvent;

		lastEventTime = getCurrentTime();
	}
	/**
	 * Medelar observatörer att state har ändrats.
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Hämtar mängden tid som biltvättar inte används
	 * @return
	 */
	public double getIdleTime() {
		return carwashIdleTime;
	}
	/**
	 * Hämtar det tids intervall det kan ta att tvätta en bil i en snabb biltvätt.
	 * 
	 * @return en array av längd två: index 0 är den undre gränsen och index 1 är
	 *         den övre gränsen.
	 */
	public double[] getFastWashTime() {
		return fastWashTime;
	}

	/**
	 * Hämtar det tids intervall det kan ta att tvätta en bil i en långsam biltvätt.
	 * 
	 * @return en array av längd två: index 0 är den undre gränsen och index 1 är
	 *         den övre gränsen.
	 */
	public double[] getSlowWashTime() {
		return slowWashTime;
	}

	/**
	 * Hämtar hur många lediga snabba biltvättar som finns.
	 * 
	 * @return mängden lediga snabba biltvättar
	 */
	public int getFreeFastWash() {
		return freeFastWash;
	}

	/**
	 * Hämtar hur många lediga långsamma biltvättar som finns.
	 * 
	 * @return mängden lediga långsamma biltvättar.
	 */
	public int getFreeSlowWash() {
		return freeSlowWash;
	}

	/**
	 * Ifall en det finns minst en ledig snabb biltvätt minskas antalet lediga med
	 * 1.
	 */
	void occupyFastWash() {
		if (getFreeFastWash() != 0) {
			freeFastWash--;
		}
	}

	/**
	 * Ifall en det finns minst en ledig långsam biltvätt minskas antalet lediga med
	 * 1.
	 */
	void occupySlowWash() {
		if (getFreeSlowWash() != 0) {
			freeSlowWash--;
		}
	}

	/**
	 * Ökar antalet lediga snabba biltvättar med 1 ifall mänden inte överskrider
	 * antalet existerande snabba biltvättar
	 */
	void freeUpFastWash() {
		if (getFreeFastWash() < fastWash) {
			freeFastWash++;
		}
	}

	/**
	 * Ökar antalet lediga långsamma biltvättar med 1 ifall mänden inte överskrider
	 * antalet existerande långsamma biltvättar
	 */
	void freeUpSlowWash() {
		if (getFreeSlowWash() < slowWash) {
			freeSlowWash++;
		}
	}

	/**
	 * Hämtar tids intervallet mellan bilars ankomst.
	 * 
	 * @return värdet på tids intervallet mellan bilars ankomst.
	 */
	public double getCarInterval() {
		return carInterval;
	}

	/**
	 * Hämtar startvärdet som slumptalsgeneratorerna använder.
	 * 
	 * @return start värdet för slumptalsgeneratorerna.
	 */
	public int getSeed() {
		return seed;
	}

}
