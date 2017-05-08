package algorithms.particleswarm;

import algorithms.datacontainer.SolutionCandidate;
import algorithms.validation.Validation;
import rabbitmq.RabbitMqClient;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Implementation of the Particle Swarm Algorithm
 * 
 * @author Philip & Gedeon
 */

public class ParticleSwarm {

	private Vector<Double> globalBest;
	private double globalBestFitness = 1000000000;
    private Particle[] swarm;
    private ArrayList<SolutionCandidate> particleSolutions;
	private int iterations;
	private int c1;
	private int c2;
	private final static int FTYPE = 3;

	public ParticleSwarm(int iterations, int amountOfParticles, int c1, int c2) {
		this.iterations = iterations;
        this.swarm = new Particle[amountOfParticles];
        this.particleSolutions = new ArrayList<SolutionCandidate>();
		this.c1 = c1;
		this.c2 = c2;
	}

    public static void main(String[] args) {
        ParticleSwarm pw = new ParticleSwarm(1000, 10000, 1, 2);
        pw.initSwarm();
        pw.letTheSwarmFly();
    }

	public void initSwarm() {
		for (int i = 0; i < swarm.length; i++) {
			Vector<Double> randomVector = new Vector<>();
			// init the position with seventy random values
			for(int j = 0; j < 17; j++){
				randomVector.addElement(Math.random() * 2);
			}
			Particle p = new Particle(randomVector, this);
			// initialize the SolutionCandidate ArrayList to map the particles
			// to the SolutionCandidates
			particleSolutions
					.add(new SolutionCandidate(new ArrayList<Double>(p.getPosition()), p.getSolutionCandidateId()));
			// fill the swarm with the random initialized values
			swarm[i] = p;
		}
	}

	public void letTheSwarmFly() {
		for (int i = 0; i < iterations; i++) {
	
            // toDo: Handle the Feasibility some how and add as third param
            particleSolutions = RabbitMqClient.getInstance().sendAndWaitForResult(particleSolutions, particleSolutions.size(), true);
            mapSwarmToSolutionCandidatesResults();

			for (int j = 0; j < swarm.length; j++) {
				if (swarm[j].getFitness() < globalBestFitness) {
					globalBestFitness = swarm[j].getFitness();
					globalBest = swarm[j].getPosition();
				}
			}

			System.out.println("GlobalBestFitness from Iteration " + (i + 1) + ": " + globalBestFitness);
			System.out.println(globalBest);

			for (int j = 0; j < swarm.length; j++) {
				swarm[j].updateVelocity();
				swarm[j].updatePosition();
			}
			
			mapSwarmToSolutionCandidates();
		}
		
		// here is end
		System.out.println("Distance: " + Validation.validate(globalBestFitness, FTYPE));
	}

	private void mapSwarmToSolutionCandidatesResults() {
		for(int i = 0; i < particleSolutions.size(); i++){
			for(int j = 0; j < swarm.length; j++){
				if(particleSolutions.get(i).getSolutionCandidateId().equals(swarm[j].getSolutionCandidateId())){
					swarm[j].setFitness(particleSolutions.get(i).getResultValue());
					//System.out.println(particleSolutions.get(i).getResultValue());
				}
			}
		}
	}
	
	private void mapSwarmToSolutionCandidates() {
		particleSolutions = new ArrayList<SolutionCandidate>();
		for(int i = 0; i < swarm.length; i++){
			particleSolutions.add(new SolutionCandidate(new ArrayList<Double>(swarm[i].getPosition()), swarm[i].getSolutionCandidateId()));
		}
	}

	public double getGlobalBestFitness() {
		return this.globalBestFitness;
	}

	public Vector<Double> getGlobalBest() {
		return globalBest;
	}

	public Particle[] getSwarm() {
		return swarm;
	}

	public int getIterations() {
		return iterations;
	}

	public int getC1() {
		return c1;
	}

	public int getC2() {
		return c2;
	}
}
