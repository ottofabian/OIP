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
		SolutionCandidate c = new GeneticAlgorithm(1000, 17).evolve((int) Math.pow(2, 12), 0.4);
		System.out.println(c.getSolutionVector());
		System.out.println(c.getResultValue());
		System.out.println(c.isFeasible());

	}
}
