package labb6.carsim;


public class CarFactory {
	private int numberOfCars;
	
	public CarFactory() {
		numberOfCars = 0;
	}
	
	 	Car createCar(double arrivalTime) {
		Car temp = new Car(numberOfCars, arrivalTime);
		numberOfCars++;
		return temp;
	}
	
	
}
