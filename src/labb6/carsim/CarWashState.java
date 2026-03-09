package labb6.carsim;

import java.util.Queue;
import java.util.LinkedList;
import labb6.generalsim.State;

/**
 * Klassen ärver state och representerar tillståndet i en biltvätts simulering.
 * Tillståndet kan endast förändras genom events.
 * 
 * @author Karl Grahn
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
	private int currentCarId;
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
		this.currentCarId = 0;
	}

	/**
	 * Kollar ifall kön till biltvätten är full.
	 * 
	 * @return sant eller falsk. Sant om kön är full. Falskt om kön inte är full.
	 * 
	 */
	public boolean carQueueIsFull() {
		if (getCurrentQueueLength() < queueLength) {
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
	 * Lägger till en bil till kön ifall kön inte är full. Ifall kön är full ökas
	 * rejected cars med 1.
	 */
	void addCar(Car car) {
		// Adds a car to carQueue if there is space
		if (!(carQueueIsFull())) {

			carQueue.add(car);
		} else {
			rejectedCars++;
		}
	}

	/**
	 * Tar bort och returnerar den bil som var först i kön.
	 * 
	 * @return den bil som var först i kön eller null ifall kön var tom.
	 */
	public Car RemoveCar() {
		Car currentCar = carQueue.poll();
		if (currentCar != null) {
			currentCarId = currentCar.getId();
		}

		return currentCar;
	}

	/**
	 * Beräknar mängden idle time per biltvätt som skapats mellan två event. Samt
	 * den sammanlagda kö tiden per bil mellan två event. Denna metod ska kallas vid
	 * varje event efter att tiden har uppdaterats.
	 */
	void updateStatistics() {
		double timeBetweenEvent = getCurrentTime() - lastEventTime;

		totalQueueTime += timeBetweenEvent * carQueue.size();
		if (carFactory.getNumberOfCars() - rejectedCars > 0) {
			meanQueueTime = totalQueueTime / (carFactory.getNumberOfCars() - rejectedCars);
		}

		int idleCarWashes = freeFastWash + freeSlowWash;

		carwashIdleTime += idleCarWashes * timeBetweenEvent;

		lastEventTime = getCurrentTime();
	}

	/**
	 * Medelar observatörer att state har ändrats och skickar med vilken typ av
	 * event som påverkade state.
	 * 
	 * @param typeOfEvent namnet på typen av event som påverkade state.
	 */
	@Override
	public void update(Object typeOfEvent) {
		super.update(typeOfEvent);

	}

	/**
	 * Hämtar den sammanlagda tiden som biltvättar står tomma.
	 * 
	 * @return den sammanlagda tiden som biltvättar står tomma.
	 */
	double getIdleTime() {
		return carwashIdleTime;
	}

	/**
	 * Hämtar den sammanlagda tiden som alla bilar spenderat i en kön.
	 * 
	 * @return den sammanlagda kötiden för alla bilar.
	 */
	double getQueueTime() {
		return totalQueueTime;
	}

	/**
	 * Häntar hur max längden på kön.
	 * 
	 * @return max längden på kön.
	 */
	int getQueueLength() {
		return queueLength;
	}

	/**
	 * Hämtar mängden bilar i bilkön.
	 * 
	 * @return mängden bilar i bilkön.
	 */
	int getCurrentQueueLength() {
		return carQueue.size();
	}

	/**
	 * Hämtar id till bilen som hanteras av eventet.
	 * 
	 * @return det relevanta bil id.
	 */
	int getCurrentCarId() {
		return currentCarId;
	}

	/**
	 * Spara bilen som hör till eventets id.
	 * 
	 * @param car bilen vars id som sparas.
	 */
	void setCurrentCarId(Car car) {
		currentCarId = car.getId();
	}

	/**
	 * Hämtar medel kötiden.
	 * 
	 * @return medel kötiden.
	 */
	double getMeanQueuTime() {
		return meanQueueTime;
	}

	/**
	 * Hämtar mängden bilar som inte fick plats i kön.
	 * 
	 * @return mängden bilar som inte fick plats i kön.
	 */
	int getRejectedCars() {
		return rejectedCars;
	}

	/**
	 * Hämtar det tids intervall det kan ta att tvätta en bil i en snabb biltvätt.
	 * 
	 * @return en array av längd två: index 0 är den undre gränsen och index 1 är
	 *         den övre gränsen.
	 */
	double[] getFastWashTime() {
		return fastWashTime;
	}

	/**
	 * Hämtar det tids intervall det kan ta att tvätta en bil i en långsam biltvätt.
	 * 
	 * @return en array av längd två: index 0 är den undre gränsen och index 1 är
	 *         den övre gränsen.
	 */
	double[] getSlowWashTime() {
		return slowWashTime;
	}

	/**
	 * Hämtar hur många snabba biltvättar som finns.
	 * 
	 * @return mängden snabba biltvättar.
	 */
	int getTotalFastWash() {
		return fastWash;
	}

	/**
	 * Hämtar hur många långsamma biltvättar som finns.
	 * 
	 * @return mängden långsamma biltvättar.
	 */
	int getTotalSlowWash() {
		return slowWash;
	}

	/**
	 * Hämtar hur många lediga snabba biltvättar som finns.
	 * 
	 * @return mängden lediga snabba biltvättar
	 */
	int getFreeFastWash() {
		return freeFastWash;
	}

	/**
	 * Hämtar hur många lediga långsamma biltvättar som finns.
	 * 
	 * @return mängden lediga långsamma biltvättar.
	 */
	int getFreeSlowWash() {
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
	 * Ökar antalet lediga snabba biltvättar med 1ifall mängden inte överskrider
	 * antalet existerande snabba biltvättar
	 */
	void freeUpFastWash() {
		if (getFreeFastWash() < fastWash) {
			freeFastWash++;
		}
	}

	/**
	 * Ökar antalet lediga långsamma biltvättar med 1, ifall mängden inte
	 * överskrider antalet existerande långsamma biltvättar
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
