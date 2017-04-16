package app;

import algorithms.datacontainer.SolutionCandidate;
import algorithms.geneticalgorithm.GeneticAlgorithm;
import algorithms.particleswarm.ParticleSwarm;

/**
 * Class OIP_App.
 * @author Daniel, Alex, Fabian
 *
 */
@SuppressWarnings("all")
public class OIP_App {
	public static void main(String[] args) {
		// GENETIC ALGORITHM ************************************************************************
		SolutionCandidate c = new GeneticAlgorithm(1000, 17).evolveOR((int) Math.pow(2, 13), 0.25);
		System.out.println("Best solution vector: " + c.getSolutionVector());
		System.out.println("According f(x): " + c.getResultValue());
		System.out.println("Feasibility: " + c.isFeasible());
		
		// PARTICLE SWARM ***************************************************************************
//		ParticleSwarm pw = new ParticleSwarm(1000, 10000, 1, 2);
//		pw.initSwarm();
//		pw.letTheSwarmFly();
	}
}
