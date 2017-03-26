package app;

import algorithms.datacontainer.SolutionCandidate;
import algorithms.geneticalgorithm.GeneticAlgorithm;

/**
 * Class OIP_App.
 * @author Daniel, Alex
 *
 */
public class OIP_App {
	public static void main(String[] args) {
		//while(true) {
		SolutionCandidate c = new GeneticAlgorithm(10000, 17).evolveOR((int) Math.pow(2, 10), 0.3);
		System.out.println("Best solution vector: " + c.getSolutionVector());
		System.out.println("According f(x): " + c.getResultValue());
		System.out.println("Feasibility: " + c.isFeasible());
		//}

	}
}
