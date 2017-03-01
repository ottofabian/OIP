package algorithms.particleswarm;


import java.util.Vector;

public class ParticleSwarm {

    public static Vector<Double> globalBest;
    public static double globalBestFitness = 1000000000;
    public static Particle[] swarm = new Particle[10];

    public static final int C1 = 1;
    public static final int C2 = 2;
    public static final int ITERATIONS = 1000;

    public class Particle{

        private Vector<Double> position;
        private double fitness = 1000000000;
        private Vector<Double> localBest;
        private Vector<Double> velocity;

        public Particle(Vector<Double> position){
            this.position = position;
            this.localBest = position;
            this.velocity = position;
        }

        public void updateVelocity(){
            velocity =  addVectors(velocity, addVectors(multiplyVector(subtractVector(localBest, position), (C1 * 0.7)),multiplyVector(subtractVector(globalBest, position) ,(C2 * 0.3))));
        }

        public void updatePosition(){
            position = addVectors(position, velocity);
        }
        
        public void updateFitness(){
        	double prevFitness = fitness;
        	double newFitness = f(position);
        	if(newFitness < prevFitness){
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

        public void setFitness(double fitness){
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

        public Vector<Double> subtractVector(Vector<Double> x, Vector<Double> y){
            Vector<Double> z = new Vector<Double>();
            for(int i = 0; i < x.size(); i++){
                z.addElement(x.get(i) - y.get(i));
            }
            return z;
        }

        public Vector<Double> multiplyVector(Vector<Double> x, double m){
            for(int i = 0; i < x.size(); i++){
                x.set(i, x.get(i) * m);
            }
            return x;
        }

        public Vector<Double> addVectors(Vector<Double> x, Vector<Double> y){
            Vector<Double> z = new Vector<Double>(x.size());
            for(int i = 0; i < x.size(); i++){
                z.addElement(x.get(i) + y.get(i));
            }
            return z;
        }
        
        public double f(Vector<Double> x){
            return Math.pow(x.get(0),2);
        }
    }

    public static void main(String[] args){

        ParticleSwarm pw = new ParticleSwarm();
        pw.initSwarm();
        pw.letTheSwarmFly();

    }

    public void initSwarm(){
        for(int i = 0; i < swarm.length; i++){
            Vector<Double> randomVector = new Vector<>();
            randomVector.addElement(Math.random()*100000-50000);
            Particle p = new Particle(randomVector);
            swarm[i] = p;
			//System.out.println("Position of particle " + i + " " + swarm[i].getPosition());

        }
    }
    
    public void letTheSwarmFly(){
    	for(int i = 0; i < ITERATIONS; i++){
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
}
