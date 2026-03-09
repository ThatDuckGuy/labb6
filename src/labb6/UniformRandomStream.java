package labb6;

import java.util.Random;

/**
 * Genererar slumpmässiga tider inom ett givet intervall. Används för att slumpa
 * fram hur lång tid en biltvätt tar.
 */
public class UniformRandomStream {

	private Random rand;
	private double lower; // den kortaste möjliga tiden
	private double width; // skillnaden mellan längsta och kortaste tiden

	/**
	 *
	 * Skapar en generator med ett bestämt intervall och seed. Med samma seed får
	 * man alltid samma tider, användbart vid testning.
	 * 
	 * @param lower undre gränsen för intervallet
	 * @param upper övre gränsen för intervallet
	 * @param seed  startvärdet för slumptalsgeneratorn
	 */
	public UniformRandomStream(double lower, double upper, long seed) {
		rand = new Random(seed);
		this.lower = lower;
		this.width = upper - lower;
	}

	/**
	 * Skapar en generator med ett bestämt intervall och slumpmässigt seed. Ger
	 * olika tider varje gång programmet körs.
	 * 
	 * @param lower undre gränsen för intervallet.
	 * @param upper övre gränsen för intervallet.
	 */

	public UniformRandomStream(double lower, double upper) {
		rand = new Random();
		this.lower = lower;
		this.width = upper - lower;
	}

	/**
	 * Returnerar nästa slumpmässiga tid inom intervallet.
	 * 
	 * @return en slumpad tid mellan lower och upper
	 */
	public double next() {
		return lower + rand.nextDouble() * width;
	}
}
