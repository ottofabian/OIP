package app;

import algorithms.DataContainer.SolutionCandidate;
import algorithms.GeneticAlorithm.GeneticAlgorithm;

/**
 * Class OIP_App.
 * @author Daniel, Alex
 *
 */
public class OIP_App {
	public static void main(String[] args) {
		SolutionCandidate c = new GeneticAlgorithm(2000).evolve((int) Math.pow(2, 12), 0.4, 17);
		System.out.println(c.getSolutionVector());
		System.out.println(c.getResultValue());
		System.out.println(c.isFeasible());

	}
}
