package algorithms.validation;

/**
 * Validation.
 * Find accuracy of results.
 * @author Alex, Fabian
 *
 */
public class Validation {
	private static double ROSENBROCK = 0;
	private static double RASTRIGIN = 0;

	private static double styblinskiTang(int dimension) {
		return -39.16617 * dimension;
	}

	/**
	 * Calculate the accuracy of the value found.
	 * @param fv
	 * @param type
	 * @return
	 */
	public static double validate(double fv, int type){
		switch (type) {
		case 1:
			return Math.abs(fv - ROSENBROCK);
		case 3:
			return Math.abs(fv - RASTRIGIN);
		case 4:
			return Math.abs(fv - styblinskiTang(17));
		}
		
		return -1.0;
	}
}
