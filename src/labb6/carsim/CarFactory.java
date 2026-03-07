package labb6.carsim;


public class CarFactory {
	private int numberOfCars;
	
	public CarFactory() {
		numberOfCars = 0;
	}
	
	public int getNumberOfCars(){
		return numberOfCars;
	}
	 	Car createCar(double arrivalTime) {
		Car temp = new Car(numberOfCars, arrivalTime);
		numberOfCars++;
		return temp;
	}
	
	
}
