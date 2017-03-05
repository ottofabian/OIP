package algorithms.ParticleSwarm;

import java.util.Vector;

/**
 * Particle Object to represent a part of the swarm
 * @author Philip & Gedeon
 *
 */
public class Particle {


	private ParticleSwarm swarm;
	private Vector<Double> position;
	private double fitness = 1000000000;
	private Vector<Double> particleBest;
	private Vector<Double> velocity;

	public Particle(Vector<Double> position, ParticleSwarm swarm) {
		this.position = position;
		this.particleBest = position;
		this.velocity = position;
		//Vector<Double> vec = new Vector<Double>();
		//this.velocity = new Vector<Double>((Math.random()*3-1)), (Math.random()*3-1));
		this.swarm = swarm;
	}

	public void updateVelocity() {
		velocity = addVectors(velocity, addVectors(multiplyVector(subtractVector(particleBest, position), (swarm.getC1() * Math.random())),
				multiplyVector(subtractVector(swarm.getGlobalBest(), position), (swarm.getC2() * Math.random()))));
	}

	public void updatePosition() {
		position = addVectors(position, velocity);
	}

	public void updateFitness() {
		double newFitness = f(position);
		if (newFitness < fitness) {
			particleBest = position;
			fitness = newFitness;
		}
	}

	public Vector<Double> getPosition() {
		return position;
	}

	public double getFitness() {
		return fitness;
	}

	public Vector<Double> getLocalBest() {
		return particleBest;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void setLocalBest(Vector<Double> localBest) {
		this.particleBest = localBest;
	}

	public Vector<Double> getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector<Double> velocity) {
		this.velocity = velocity;
	}

	public void setPosition(Vector<Double> position) {
		this.position = position;
	}

	public Vector<Double> subtractVector(Vector<Double> x, Vector<Double> y) {
		Vector<Double> z = new Vector<Double>();
		for (int i = 0; i < x.size(); i++) {
			z.addElement(x.get(i) - y.get(i));
		}
		return z;
	}

	public Vector<Double> multiplyVector(Vector<Double> x, double m) {
		Vector<Double> z = new Vector<Double>();

		for (int i = 0; i < x.size(); i++) {
			z.addElement(x.get(i) * m);
		}

		return z;
	}

	public Vector<Double> addVectors(Vector<Double> x, Vector<Double> y) {
		Vector<Double> z = new Vector<Double>(x.size());
		for (int i = 0; i < x.size(); i++) {
			z.addElement(x.get(i) + y.get(i));
		}
		return z;
	}

	public double f(Vector<Double> x) {
		return Math.pow(x.get(0), 2);
	}
}
