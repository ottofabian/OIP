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
		SolutionCandidate c = new GeneticAlgorithm(100).evolve((int) Math.pow(2, 5), 0.2, 17);
		System.out.println(c.getSolutionVector());
		System.out.println(c.getResultValue());
	}
}
