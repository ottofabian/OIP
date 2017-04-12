package app;

import algorithms.datacontainer.SolutionCandidate;
import algorithms.geneticalgorithm.GeneticAlgorithm;

/**
 * Class OIP_App.
 * @author Daniel, Alex, Fabian
 *
 */
public class OIP_App {
	public static void main(String[] args) {
		SolutionCandidate c = new GeneticAlgorithm(1000, 17).evolveOR((int) Math.pow(2, 13), 0.25);
		System.out.println("Best solution vector: " + c.getSolutionVector());
		System.out.println("According f(x): " + c.getResultValue());
		System.out.println("Feasibility: " + c.isFeasible());
	}
}
