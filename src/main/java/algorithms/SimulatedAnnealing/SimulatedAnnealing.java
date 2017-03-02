package algorithms.SimulatedAnnealing;

/**
 * Simulated Annealing.
 * @author Daniel
 *
 */
public class SimulatedAnnealing {
	
	public static void main(String[] args) {
		System.out.println("Result " + start(5, 40000, 1, 0.999, 50000));
	}
	
	/**
	 * Function.
	 * @param x
	 * @return
	 */
	private static double f(double x) {
		return 0.5 * Math.pow(x, 6) + Math.pow(x, 5) + Math.pow(x, 2);
	}
	
	/**
	 * Get probability for accepting the new value.
	 * @param d (delta new value <-> old value)
	 * @param t (current temperature)
	 * @return
	 */
	private static double acceptance(double d, double t) {
		if(d >= 0) {
			return 1.0;
		}
		return Math.exp((d / t));
	}
	
	/**
	 * Start the simulated annealing process.
	 * @param x (start value for x)
	 * @param t (initial temperature)
	 * @param t_min (minimum temperature)
	 * @param cool (cooling rate)
	 * @param l (number of iterations with same temperature)
	 * @return best found value
	 */
	private static double start(double x, double t, double t_min, double cool, int l) {
		while(t >= t_min) {
			for(int i = 1; i <= l; i++) {
				double x_new = (Math.random() * 20) - 10;				
				if(Math.random() < acceptance(f(x) - f(x_new), t)) {
					x = x_new;
				}
			}
			t *= cool;
		}
		return x;
	}
}