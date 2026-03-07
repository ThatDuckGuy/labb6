package labb6.carsim;

/**
 * 
 */
public class Car {
	private int id;
	private double arrivalTime;

	Car(int id, double arrivalTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Hämtar den tid bilen kom till billtvätten.
	 * 
	 * @return tiden bilen kom till biltvätten
	 */
	public double getArrivalTime() {
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