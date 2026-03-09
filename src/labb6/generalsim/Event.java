package labb6.generalsim;

/**
 * Abstrakt klass som representerar en generell händelse i simuleringen. Varje
 * händelse har en tid när händelsen inträffar, och varje händelse påverkar
 * state. Händelser ordnas utifrån denna tid genom {@Code comparable}.
 *
 * @author Hugo Igelström
 */
public abstract class Event implements Comparable<Event> {

	private final double time;

	/**
	 * Skapar en händelse som inträffar vid en given tid.
	 *
	 * @param time tiden då händelsen sker
	 */
	public Event(double time) {
		this.time = time;
	}

	/**
	 * En getter som returnerar tiden då en händelse inträffar.
	 * 
	 * @return tiden då en händelse sker
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Jämför en händelse (this) med en annan (other) baserat på tid, och avgör
	 * vilken som inträffar först.
	 *
	 * @param other händelsen som jämförelsen ska utföras på.
	 * @return ett tal n, som uppfyller följande:
	 *         <ul>
	 *         <li>om n &lt; 0 : this inträffar först.</li>
	 *         <li>om n = 0 : this och other inträffar samtidigt.</li>
	 *         <li>om n &gt; 0 : other inträffar först.</li>
	 *         </ul>
	 */
	@Override
	public int compareTo(Event other) {
		return Double.compare(this.time, other.time); // wrapper används för att kunna kalla .compare()
	}

	/**
	 * Utför händelsens effekt på simuleringens tillstånd (State s). Konkretiseras
	 * av varje implementation till Event.
	 *
	 * @param s simuleringens nuvarande tillstånd
	 */
	public abstract void occur(State s);
}