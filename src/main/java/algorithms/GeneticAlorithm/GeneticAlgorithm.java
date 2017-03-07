package algorithms.GeneticAlorithm;

import algorithms.DataContainer.SolutionCandidate;
import rabbitmq.RabbitMqClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Genetic algorithm.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class GeneticAlgorithm {

    // toDo: Has to be established as dynamic value
    private final int ITERATIONS;
    private int UPPERLIMIT = 50;
    private int LOWERLIMIT = UPPERLIMIT - 2 * UPPERLIMIT;
    private double MUTATION = 0.05;
    //private double CROSSOVER = 0.8;
    private double FEASIBLELIMIT = 0.75;
    private boolean checkFeasible = false;


    public GeneticAlgorithm(int iter) {
        ITERATIONS = iter;
    }

	/**
	 * Start the evolution.
	 * @param pop (number of population members => 2^x)
	 * @param part_pec (percentage for partitioning)
	 * @param dim (dimension of target function)
	 * @para iter (number of iterations)
	 * @return
	 */
    public SolutionCandidate evolve(int pop, double part_perc, int dim) {

        // init population (first generation)
        ArrayList<SolutionCandidate> c = initPopulation(pop, dim);
		Collections.sort(c);

        int iter = ITERATIONS;
        Random r = new Random();

        // calculate partition index
        int part_index = (int) (pop * part_perc);

		// evolution
		while(iter > 0) {
			int i = part_index;
			
			while(i < pop) {
				for(int j = 0; j < part_index; j++) {

                    // Random number for decision between mutation or crossover
                    if (r.nextInt(100) < MUTATION * 100) {
                        mutate(c.get(j), r);
                    } else {
                        int rand = (int) (Math.random() * part_index);
                        c.set(i, mate(c.get(j), c.get(rand)));
                    }

					if(++i >= pop) break;
				}
			}
			
			System.out.println("Iteration: " + iter);

            c = RabbitMqClient.getInstance().sendAndWaitForResult(c, pop, checkFeasible);
            Collections.sort(c);
            iter--;
            if (iter < (ITERATIONS - (FEASIBLELIMIT * ITERATIONS))) {
                checkFeasible = true;
            }
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
	 * Create a child solution candidate.
	 * @param mateOne
	 * @param mateTwo
	 * @return
	 */
	private SolutionCandidate mate(SolutionCandidate mateOne, SolutionCandidate mateTwo) {	
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

    /**
     * Mutate random values
     *
     * @param c
     * @param Random
     * @return
     */
    private SolutionCandidate mutate(SolutionCandidate c, Random r) {

        // toDo: Probably change amount of mutations

        ArrayList<Double> t = c.getSolutionVector();
        int elem = r.nextInt(t.size() + 1);

        double gauss = next_gaussian(r);
        double newValue = t.get(elem) + gauss;

        if (newValue > UPPERLIMIT) {
            newValue = UPPERLIMIT;
        } else if (newValue < LOWERLIMIT) {
            newValue = LOWERLIMIT;
        }

        t.set(elem, newValue);

        return c;
    }

    /**
     * Random Gaussian value for Gaussian mutation
     *
     * @return
     */
    private double next_gaussian(Random r) {
        // ToDo: Adjust Gauss distribution
        // Generate an initial [-1,1] gaussian distribution
        // Quantize to step size 0.00001
        return Math.rint((r.nextGaussian()) * 100000.0) * 0.00001;
    }

	/*
	 * Test functions.
	 */
	
//	/**
//	 * Rosenbrock function.
//	 * @param x
//	 * @return
//	 */
//	private double f(ArrayList<Double> x) {
//		double sum = 0;
//		
//		for(int i = 0; i < (x.size() - 1); i++) {
//			sum += 100 * Math.pow((x.get(i + 1) - Math.pow(x.get(i), 2)), 2)
//				+ Math.pow((1 - x.get(i)), 2);
//		}
//		return sum;
//	}
//	
//	/**
//	 * Rastrigin function.
//	 * @param x
//	 * @return
//	 */
//	private double g(ArrayList<Double> x) {
//		double sum = 10 * x.size();
//		
//		for(int i = 0; i < x.size(); i++) {
//			sum += Math.pow(x.get(i), 2) - 10 * Math.cos(2 * Math.PI * x.get(i));
//		}
//		return sum;
//	}
}