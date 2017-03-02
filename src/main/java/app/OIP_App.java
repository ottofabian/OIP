package app;

import algorithms.GeneticAlorithm.GeneticAlgorithm;
import algorithms.DataContainer.SolutionCandidate;

/**
 * Class OIP_App.
 * @author Daniel, Alex
 *
 */
public class OIP_App {
	public static void main(String[] args) {
		SolutionCandidate c = new GeneticAlgorithm(100).evolve((int) Math.pow(2, 15), 0.2, 17);
		System.out.println(c.getSolutionVector());
		System.out.println(c.getResultValue());
	}
}
