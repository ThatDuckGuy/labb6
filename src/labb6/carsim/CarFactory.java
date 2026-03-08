package labb6.carsim;

/**
 * Skapar instanser av klassen bil, samt håller koll på hur många bjlar som har
 * skapats.
 */
public class CarFactory {
	private int numberOfCars;

	/**
	 * Skapar en ny CarFactory och sätter mängder av bilar som har skapats till 0.
	 */
	public CarFactory() {
		numberOfCars = 0;
	}

	/**
	 * Hämtar antalet bilar som CarFactory har gjort.
	 * 
	 * @return antalet bilar som har skapats.
	 */
	public int getNumberOfCars() {
		return numberOfCars;
	}
	/**
	 * Skapar en bil och returnerar bilen.
	 * @param arrivalTime tiden då bilen skapades.
	 * @return en bil
	 */
	Car createCar(double arrivalTime) {
		Car temp = new Car(numberOfCars, arrivalTime);
		numberOfCars++;
		return temp;
	}

}
