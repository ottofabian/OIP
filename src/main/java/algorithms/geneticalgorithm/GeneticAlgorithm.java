package algorithms.geneticalgorithm;

import algorithms.datacontainer.SolutionCandidate;
import algorithms.validation.Validation;
import rabbitmq.RabbitMqClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

/**
 * Genetic algorithm.
 * @author Daniel
 *
 */
@SuppressWarnings("all")
public class GeneticAlgorithm {
	
	private final Random r = new Random();
	
	//dimension of function
	private final int DIMENSION;

	//number of total iterations
    private final int ITERATIONS;
    
    //search space limits
    private int UPPERLIMIT = 10;
    private int LOWERLIMIT = UPPERLIMIT / 2;
    
    //mutation rates
    private double MUTATION = 0.05;
    private int MUTATION_SWITCH_N = 6;
    
    private double FEASIBLELIMIT = 0.75;
    private boolean checkFeasible = false;
    
    //fucntion type
    private final static int FTYPE = 1;

    /**
     * Constructor.
     * 
     */
    public GeneticAlgorithm(int iter, int dim) {
        ITERATIONS = iter;
        DIMENSION = dim;
    }

	/**
	 * Start the evolution.
	 * @param pop (number of population members => 2^x)
	 * @param part_pec (percentage for partitioning)
	 * @para iter (number of iterations)
	 * @return
	 */
    public SolutionCandidate evolve(int pop, double part_perc) {

        // init population (first generation)
        ArrayList<SolutionCandidate> c = initPopulation(pop);
		Collections.sort(c);

        int iter = ITERATIONS;

        // calculate partition index
        int part_index = (int) (pop * part_perc);

		// evolution
		while(iter > 0) {
			int i = part_index;
			
			while(i < pop) {
				for(int j = 0; j < part_index; j++) {

                    // Random number for decision between mutation or crossover
                    if (r.nextInt(100) < MUTATION * 100) {
                        mutateSwitchN(c.get(j), MUTATION_SWITCH_N);
                    } else {
                        int rand = (int) (Math.random() * part_index);
                        c.set(i, mateXover(c.get(j), c.get(rand)));
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
		System.out.println("Distance: " + Validation.validate(c.get(0).getResultValue(), FTYPE));
        return c.get(0);
	}

	/**
	 * Initialize population randomly.
	 * @param pop (number of population members)
	 * @return
	 */
	private ArrayList<SolutionCandidate> initPopulation(int pop) {
		ArrayList<SolutionCandidate> c = new ArrayList<>();
		
		for(int i = 0; i < pop; i++) {
			// random vector x
			ArrayList<Double> x = new ArrayList<>();
			for(int j = 0; j < DIMENSION; j++) {
                x.add((Math.random() * UPPERLIMIT) - LOWERLIMIT);
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
	private SolutionCandidate mateAvg(SolutionCandidate mateOne, SolutionCandidate mateTwo) {	
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
	 * Mate two candidates using a crossover strategy.
	 * @param mateOne
	 * @param mateTwo
	 * @return
	 */
	private SolutionCandidate mateXover(SolutionCandidate mateOne, SolutionCandidate mateTwo) {
		int partitionIdx = (int) (Math.random() * DIMENSION);
		if(partitionIdx == 0 || partitionIdx == 16) partitionIdx = (int) (DIMENSION / 2);
		
		ArrayList<Double> p1 = new ArrayList<>(); p1.addAll(mateOne.getSolutionVector());
		ArrayList<Double> p2 = new ArrayList<>(); p2.addAll(mateTwo.getSolutionVector());
		
		//get sublists of parent solution vectors
		List<Double> p1Head = p1.subList(0, partitionIdx);
		List<Double> p2Tail = p2.subList(partitionIdx, DIMENSION);

		//combine sublists to form a new solution vector
		p1Head.addAll(p2Tail);
		ArrayList<Double> result = new ArrayList<>();
		result.addAll(p1Head);
		
		return new SolutionCandidate(result);
	}

    /**
     * Mutate by using gaussian random numbers.
     * @param c
     * @param Random
     * @return
     */
    private void mutateGauss(SolutionCandidate c) {
        ArrayList<Double> t = c.getSolutionVector();
        int elem = r.nextInt(t.size() - 1);

        double gauss = next_gaussian();
        double newValue = t.get(elem) + gauss;

        if (newValue > UPPERLIMIT / 2) {
            newValue = UPPERLIMIT;
        } else if (newValue < LOWERLIMIT) {
            newValue = LOWERLIMIT;
        }

        t.set(elem, newValue);
    }
    
    /**
     * Mutate by switching elements.
     * @param c
     * @param n
     */
    public void mutateSwitchN(SolutionCandidate c, int n) {
    	ArrayList<Double> sv = c.getSolutionVector();
    	
    	//Generate n uniformly distributed random numbers
    	List<Integer> pos = Stream.generate(() -> (int) (Math.random() * DIMENSION))
    		.distinct()
    		.limit(n)
    		.collect(Collectors.toList());
    	
    	//switch positions
    	for(int i = 0; i < pos.size(); i += 2) {
    		int idx1 = pos.get(i);
    		int idx2 = pos.get(i + 1);
    		double pos1 = c.getSolutionVector().get(idx1);
    		
    		sv.set(idx1, sv.get(idx2));
    		sv.set(idx2, pos1);
    	}
    }

    /**
     * Random Gaussian value for Gaussian mutation
     * @return
     */
    private double next_gaussian() {
        // Generate an initial [-1,1] gaussian distribution
        // Quantize to step size 0.001
        return Math.rint((r.nextGaussian()) * 1000.0) * 0.001;
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