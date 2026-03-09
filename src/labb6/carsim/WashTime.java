package labb6.carsim;

import labb6.ExponentialRandomStream;
import labb6.UniformRandomStream;

/**
 * Hjälpklass för att beräkna tiden för olika events.
 *
 * @author Hugo Igelström
 */
public class WashTime {

	private final ExponentialRandomStream arrivalStream;
	private final UniformRandomStream fastWashStream;
	private final UniformRandomStream slowWashStream;

	/**
	 * Skapar ett nytt WashTime objekt med givna slumptalsgeneratorer.
	 *
	 * @param arrivalStream  RNG för ankomsttider
	 * @param fastWashStream RNG för snabb tvätt
	 * @param slowWashStream RNG för långsam tvätt
	 */
	public WashTime(ExponentialRandomStream arrivalStream, UniformRandomStream fastWashStream,
			UniformRandomStream slowWashStream) {
		this.arrivalStream = arrivalStream;
		this.fastWashStream = fastWashStream;
		this.slowWashStream = slowWashStream;
	}

	/**
	 * Beräknar tiden då nästa bil anländer.
	 *
	 * @param currentTime simuleringens nuvarande tid
	 * @return tidpunkten då nästa bil anländer
	 */
	public double nextArrival(double currentTime) {
		return currentTime + arrivalStream.next();
	}

	/**
	 * Beräknar tiden då en bil är färdig i den snabba maskinen.
	 *
	 * @param currentTime simuleringens nuvarande tid
	 * @return tidpunkten då bilen lämnar den snabba maskinen
	 */
	public double nextFast(double currentTime) {
		return currentTime + fastWashStream.next();
	}

	/**
	 * Beräknar tiden då en bil är färdig i den långsamma maskinen.
	 *
	 * @param currentTime simuleringens nuvarande tid
	 * @return tidpunkten då bilen lämnar den långsamma maskinen
	 */
	double nextSlow(double currentTime) {
		return currentTime + slowWashStream.next();
	}
}