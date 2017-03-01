package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Genetic algorithm.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class GeneticAlgorithm {
	
	/**
	 * Class Creature.
	 * @author Daniel
	 *
	 */
	public class Creature implements Comparable<Creature>{
		private Vector<Double> x;
		private double fitness;
		
		/**
		 * Constructor Creature.
		 * @param x
		 */
		public Creature(Vector<Double> x) {
			this.x = x;
			this.fitness = f(x);
		}
		
		/**
		 * Get x value.
		 * @return
		 */
		public Vector<Double> getX() {
			return this.x;
		}
		
		/**
		 * Get fitness value (= y value).
		 * @return
		 */
		public double getFitness() {
			return this.fitness;
		}
		
		/**
		 * Create a child creature.
		 * @param mate
		 * @return
		 */
		public Creature mate(Creature mate) {	
			Vector<Double> x = new Vector<>();
			
			// weighted avg: this.x * mate.fitness + mate.x * this.fitness / (sum(fitness))
			for(int i = 0; i < this.x.size(); i++) {
				x.add((this.x.get(i) * mate.getFitness() + mate.getX().get(i) * this.fitness)
				/ (mate.getFitness() + this.fitness));
			}
			return new Creature(x);
		}

		@Override
		public int compareTo(Creature o) {
			return Double.compare(this.fitness, o.getFitness());
		}
		
		/**
		 * Rosenbrock Target function.
		 * @param x
		 * @return
		 */
		private double f(Vector<Double> x) {
			double sum = 0;
			
			for(int i = 0; i < (x.size() - 1); i++) {
				sum += 100 * Math.pow((x.get(i + 1) - Math.pow(x.get(i), 2)), 2)
					+ Math.pow((1 - x.get(i)), 2);
			}
			return sum;
		}
		
		/**
		 * Rastrigin Target function.
		 * @param x
		 * @return
		 */
		private double g(Vector<Double> x) {
			double sum = 10 * x.size();
			
			for(int i = 0; i < x.size(); i++) {
				sum += Math.pow(x.get(i), 2) - 10 * Math.cos(2 * Math.PI * x.get(i));
			}
			return sum;
		}
	}

	public static void main(String[] args) {
		Creature c = new GeneticAlgorithm().beginEvolution((int) Math.pow(2, 20), 0.2, 6, 200);
		System.out.println(c.getX());
		System.out.println(c.getFitness());
	}
	
	/**
	 * Start the evolution.
	 * @param pop (number of population members => 2^x)
	 * @param dim (dimension of target function)
	 * @return
	 */
	private Creature beginEvolution(int pop, double part_perc, int dim, int iter) {
		
		// init population (first generation)
		ArrayList<Creature> c = initPopulation(pop, dim);
		Collections.sort(c);
		
		// calculate partition index
		int part_index = (int) (pop * part_perc);

		// evolution
		while(iter > 0) {
			int i = part_index;
			
			while(i < pop) {
				for(int j = 0; j < part_index; j++) {
					int rand = (int) (Math.random() * part_index);
					c.set(i, c.get(j).mate(c.get(rand)));
					if(++i >= pop) break;
				}
			}
			System.out.println("Iteration: " + iter);
			
			// TODO: Call sender and receiver
			
			Collections.sort(c);
			iter--; 
		}
		return c.get(0);
	}

	/**
	 * Initialize population randomly.
	 * @param pop (number of population members)
	 * @param dim (dimension of target function)
	 * @return
	 */
	private ArrayList<Creature> initPopulation(int pop, int dim) {
		ArrayList<Creature> c = new ArrayList<>();
		
		for(int i = 0; i < pop; i++) {
			// random vector x
			Vector<Double> x = new Vector<>();
			for(int j = 0; j < dim; j++) {
				x.add((Math.random() * 100) - 50);
			}
			c.add(this.new Creature(x));
		}
		
		return c;
	}
}