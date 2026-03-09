package labb6.carsim;

/**
 * Representerar bilar som åker in i biltvätten. Till varje bil som skapas ett
 * id som tillhör den bilen.
 * 
 * @author Karl Grahn
 */
public class Car {
	private int id;
	private double arrivalTime;

	Car(int id, double arrivalTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Hämtar den bilens id.
	 * 
	 * @return bilens id.
	 */
	int getId() {
		return id;
	}
}