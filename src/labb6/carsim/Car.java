package labb6.carsim;

/**
 * Representerar bilar som åker in i biltvätten. Varje bil har ett id och
 */
public class Car {
	private int id;
	private double arrivalTime; // Behövs förmodligen inte längre
	
	Car(int id, double arrivalTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Hämtar den tid bilen kom till billtvätten.
	 * 
	 * @return tiden bilen kom till biltvätten
	 */
	public double getArrivalTime() { // Behövs förmodligen inte längre
		return arrivalTime;
	}

	/**
	 * Hämtar den bilens id.
	 * 
	 * @return bilens id.
	 */
	public int getId() {
		return id;
	}
}