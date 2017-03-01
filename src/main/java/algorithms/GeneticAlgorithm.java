package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import rabbitmq.Receiver;
import rabbitmq.Sender;

/**
 * Genetic algorithm.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class GeneticAlgorithm {
	
//	/**
//	 * Class Creature.
//	 * @author Daniel
//	 *
//	 */
//	public class Creature implements Comparable<Creature>{
//		private ArrayList<Double> x;
//		private double fitness;
//		
//		/**
//		 * Constructor Creature.
//		 * @param x
//		 */
//		public Creature(ArrayList<Double> x) {
//			this.x = x;
//			this.fitness = f(x);
//		}
//		
//		/**
//		 * Get x value.
//		 * @return
//		 */
//		public ArrayList<Double> getX() {
//			return this.x;
//		}
//		
//		/**
//		 * Get fitness value (= y value).
//		 * @return
//		 */
//		public double getFitness() {
//			return this.fitness;
//		}
//		
//		/**
//		 * Create a child creature.
//		 * @param mate
//		 * @return
//		 */
//		public Creature mate(Creature mate) {	
//			ArrayList<Double> x = new ArrayList<>();
//			
//			// weighted avg: this.x * mate.fitness + mate.x * this.fitness / (sum(fitness))
//			for(int i = 0; i < this.x.size(); i++) {
//				x.add((this.x.get(i) * mate.getFitness() + mate.getX().get(i) * this.fitness)
//				/ (mate.getFitness() + this.fitness));
//			}
//			return new Creature(x);
//		}
//
//		@Override
//		public int compareTo(Creature o) {
//			return Double.compare(this.fitness, o.getFitness());
//		}
//		
//		/**
//		 * Rosenbrock Target function.
//		 * @param x
//		 * @return
//		 */
//		private double f(ArrayList<Double> x) {
//			double sum = 0;
//			
//			for(int i = 0; i < (x.size() - 1); i++) {
//				sum += 100 * Math.pow((x.get(i + 1) - Math.pow(x.get(i), 2)), 2)
//					+ Math.pow((1 - x.get(i)), 2);
//			}
//			return sum;
//		}
//		
//		/**
//		 * Rastrigin Target function.
//		 * @param x
//		 * @return
//		 */
//		private double g(ArrayList<Double> x) {
//			double sum = 10 * x.size();
//			
//			for(int i = 0; i < x.size(); i++) {
//				sum += Math.pow(x.get(i), 2) - 10 * Math.cos(2 * Math.PI * x.get(i));
//			}
//			return sum;
//		}
//	}
	
	/**
	 * Start the evolution.
	 * @param pop (number of population members => 2^x)
	 * @param part_pec (percentage for partitioning)
	 * @param dim (dimension of target function)
	 * @para iter (number of iterations)
	 * @return
	 */
	public SolutionCandidate evolve(int pop, double part_perc, int dim, int iter) {
		
		// init population (first generation)
		ArrayList<SolutionCandidate> c = initPopulation(pop, dim);
		Collections.sort(c);
		
		// calculate partition index
		int part_index = (int) (pop * part_perc);

		// evolution
		while(iter > 0) {
			int i = part_index;
			
			while(i < pop) {
				for(int j = 0; j < part_index; j++) {
					int rand = (int) (Math.random() * part_index);
					c.set(i, mate(c.get(j), c.get(rand)));
					if(++i >= pop) break;
				}
			}
			System.out.println("Iteration: " + iter);
			
			// TODO: Call sender and receiver
			new Sender().send(c);
			
			CountDownLatch latch = new CountDownLatch(1);
			Receiver receiver = new Receiver(latch, pop);
			new Thread(receiver).start();
			
			try {
				// block call, wait for receiver
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			c = receiver.getResults();
			System.out.println("Received package");
			
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
	private ArrayList<SolutionCandidate> initPopulation(int pop, int dim) {
		ArrayList<SolutionCandidate> c = new ArrayList<>();
		
		for(int i = 0; i < pop; i++) {
			// random vector x
			ArrayList<Double> x = new ArrayList<>();
			for(int j = 0; j < dim; j++) {
				x.add((Math.random() * 100) - 50);
			}
			c.add(new SolutionCandidate(x));
		}
		
		return c;
	}
	
	/**
	 * Create a child creature.
	 * @param mateOne
	 * @param mateTwo
	 * @return
	 */
	public SolutionCandidate mate(SolutionCandidate mateOne, SolutionCandidate mateTwo) {	
		ArrayList<Double> x = new ArrayList<>();
		
		// weighted average
		for(int i = 0; i < mateOne.getSolutionVector().size(); i++) {
			x.add(
				(mateOne.getSolutionVector().get(i) * mateTwo.getResultValue()
					+ mateTwo.getSolutionVector().get(i) * mateOne.getResultValue())
				/ (mateTwo.getResultValue() + mateOne.getResultValue())
			);
		}
		return new SolutionCandidate(x);
	}
}