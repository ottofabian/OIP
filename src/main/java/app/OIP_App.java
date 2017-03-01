package app;

import algorithms.GeneticAlgorithm;
import algorithms.SolutionCandidate;

/**
 * Class OIP_App.
 * @author Daniel, Alex
 *
 */
public class OIP_App {
	public static void main(String[] args) {
		SolutionCandidate c = new GeneticAlgorithm().evolve((int) Math.pow(2, 15), 0.2, 17, 100);
		System.out.println(c.getSolutionVector());
		System.out.println(c.getResultValue());
	}
}
