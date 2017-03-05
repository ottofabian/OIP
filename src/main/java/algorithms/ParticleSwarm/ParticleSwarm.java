package algorithms.ParticleSwarm;
import java.util.Vector;

/**
 * Implementation of the Particle Swarm Algorithm
 * @author Philip & Gedeon
 * Comments incoming when bug is fixed
 */

public class ParticleSwarm {

	private Vector<Double> globalBest;
	private double globalBestFitness = 1000000000;
    private Particle[] swarm;
    private int iterations;
	private int c1;
	private int c2;

    public static void main(String[] args){
        ParticleSwarm pw = new ParticleSwarm(10000, 10000, 1, 2);
        pw.initSwarm();
        pw.letTheSwarmFly();
    }
    
    public ParticleSwarm(int iterations, int amountOfParticles, int c1, int c2){
    	this.iterations = iterations;
    	this.swarm = new Particle[amountOfParticles];
    	this.c1 = c1;
    	this.c2 = c2;
    }
    
    public void initSwarm(){
        for(int i = 0; i < swarm.length; i++){
            Vector<Double> randomVector = new Vector<>();
            randomVector.addElement(Math.random()*100000-50000);
            Particle p = new Particle(randomVector, this);
            swarm[i] = p;
			//System.out.println("Position of particle " + i + " " + swarm[i].getPosition());
        }
    }
    
    public void letTheSwarmFly(){
    	for(int i = 0; i < iterations; i++){
    		for(int j = 0; j < swarm.length; j++){
    			swarm[j].updateFitness();
    		}
    		
    		for(int j = 0; j < swarm.length; j++){
    			if(swarm[j].getFitness() < globalBestFitness){
    				globalBestFitness = swarm[j].getFitness();
    				globalBest = swarm[j].getPosition();
    			}
    		}
    		
    		System.out.println("Statistics from Iteration " + (i+1) + ": " + globalBest + " " + globalBestFitness);

    		for(int j = 0; j < swarm.length; j++){
    			swarm[j].updateVelocity();
    			swarm[j].updatePosition();
    			//System.out.println("Position of particle " + j + " " + swarm[j].getPosition() + " " + swarm[j].getFitness());
    		}
    	}
    }
    
    public double getGlobalBestFitness(){
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
