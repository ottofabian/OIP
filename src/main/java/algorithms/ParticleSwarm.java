package algorithms;


import java.util.Vector;

public class ParticleSwarm {

    public static Vector<Double> globalBest;
    public static double globalBestFitness = 10000000;
    public static final int C1 = 1;
    public static final int C2 = 2;
    public static Particle[] swarm = new Particle[100];

    public class Particle{

        private Vector<Double> position;
        private double fitness;
        private Vector<Double> localBest;
        private Vector<Double> velocity;

        public Particle(Vector<Double> position){
            this.position = position;
            this.localBest = position;
            this.velocity = position;
        }

        public void updateVelocity(){
            velocity =  addVectors(velocity, addVectors(multiplyVector(subtractVector(localBest, position), (C1 * Math.random())),multiplyVector(subtractVector(globalBest, position) ,(C2 * Math.random()))));
        }

        public void updatePosition(){
            position = addVectors(position, velocity);
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
            Vector<Double> z = new Vector<Double>(x.size());
            for(int i = 0; i < x.size(); i++){
                z.set(i, x.get(i) - y.get(i));
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
                z.set(i, x.get(i) + y.get(i));
            }
            return z;
        }
    }

    public static void main(String[] args){

        ParticleSwarm pw = new ParticleSwarm();
        pw.initSwarm();
        System.out.println(globalBest + " " + globalBestFitness);

    }

    public void initSwarm(){
        for(int i = 0; i < 100; i++){
            Vector<Double> randomVector = new Vector<>();
            randomVector.addElement(Math.random()*10-5);
            Particle p = new Particle(randomVector);
            p.setFitness(f(p.getPosition()));
            if(p.getFitness() < globalBestFitness){
                globalBestFitness = p.getFitness();
                globalBest = p.getPosition();
            }
            swarm[i] = p;
        }
    }

    public double f(Vector<Double> x){
        return Math.pow(x.get(0),2);
    }

}
