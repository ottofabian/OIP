package algorithms.particleswarm;

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
	private Vector<Double> localBest;
	private Vector<Double> velocity;

	public Particle(Vector<Double> position, ParticleSwarm swarm) {
		this.position = position;
		this.localBest = position;
		this.velocity = position;
		this.swarm = swarm;
	}

	public void updateVelocity() {
		velocity = addVectors(velocity, addVectors(multiplyVector(subtractVector(localBest, position), (swarm.getC1() * 0.7)),
				multiplyVector(subtractVector(swarm.getGlobalBest(), position), (swarm.getC2() * 0.3))));
	}

	public void updatePosition() {
		position = addVectors(position, velocity);
	}

	public void updateFitness() {
		double prevFitness = fitness;
		double newFitness = f(position);
		if (newFitness < prevFitness) {
			localBest = position;
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
		return localBest;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void setLocalBest(Vector<Double> localBest) {
		this.localBest = localBest;
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
