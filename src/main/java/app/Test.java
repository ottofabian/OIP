package app;

import algorithms.GeneticAlgorithm;
import algorithms.SolutionCandidate;

/**
 * Class Test.
 * @author Daniel, Alex
 *
 */
public class Test {
	public static void main(String[] args) {
		SolutionCandidate c = new GeneticAlgorithm().evolve((int) Math.pow(2, 10), 0.2, 17, 20);
		System.out.println(c.getSolutionVector());
		System.out.println(c.getResultValue());
	}
}
