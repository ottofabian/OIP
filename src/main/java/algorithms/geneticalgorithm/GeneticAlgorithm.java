package algorithms.geneticalgorithm;

import static java.lang.StrictMath.exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import algorithms.datacontainer.SolutionCandidate;
import algorithms.validation.Validation;
import rabbitmq.RabbitMqClient;

/**
 * Genetic algorithm.
 * @author Daniel, Fabian
 */
@SuppressWarnings("all")
public class GeneticAlgorithm {

    //function type
    private final static int FTYPE = 2;
    private final Random r = new Random();
    
    //dimension of function
    private final int DIMENSION;
    
    //number of total iterations
    private final int ITERATIONS;
    
    //search space limits
    private int UPPERLIMIT = 4;
    private int LOWERLIMIT = UPPERLIMIT / 2;
    
    //mutation rates
    private double MUTATION = 0.3;
    private double MUTATION_RATE = 0.08;
    private int MUTATIONS_PER_CREATURE = 4;
    private double FEASIBLELIMIT = 0.0;
    private boolean checkFeasible = true;

    /**
     * Constructor.
     * @Param iter 	(number of iterations)
     * @Param dim	(dimension)
     */
    public GeneticAlgorithm(int iter, int dim) {
        ITERATIONS = iter;
        DIMENSION = dim;
    }

    /**
     * Start the evolution.
     * This method uses either mutation or crossover operations.
     * @param pop      (number of population members => 2^x)
     * @param part_pec (percentage for partitioning)
     * @return
     */
    public SolutionCandidate evolveXOR(int pop, double tournament_perc) {

        // init population (first generation)
        ArrayList<SolutionCandidate> c = initPopulation(pop);
        Collections.sort(c);

        int iter = ITERATIONS;

        // calculate partition index
        int part_index = (int) (pop * tournament_perc);

        // evolution
        while (iter > 0) {
            int i = part_index;

            while (i < pop) {
                for (int j = 0; j < part_index; j++) {

                    SolutionCandidate sc = c.get(j);

                    // Random number for decision between mutation or crossover
                    if (Math.random() < MUTATION) {
                        //c.set(i,mutateGauss(c.get(j),MUTATIONS_PER_CREATURE));
                        //c.set(i,mutateSwitchN(sc, MUTATIONS_PER_CREATURE));
                        c.set(i, mutateRandom(c.get(j), MUTATIONS_PER_CREATURE));

                    } else {
                        int rand = (int) (Math.random() * part_index);
                        //c.set(i, mateAvg(sc, c.get(rand)));
                        c.set(i, mateSinglePointXover(sc, c.get(rand)));
                        //c.set(i, mateMultiPointXover(sc, c.get(rand)));

                    }

                    if (++i >= pop) break;
                }
            }

            c = RabbitMqClient.getInstance().sendAndWaitForResult(c, pop, checkFeasible);
            Collections.sort(c);

            System.out.println("Iteration: " + iter);
            System.out.println("Current top value: " + c.get(0).getResultValue() + " (" + c.get(0).isFeasible() + ")");

            iter--;
            //adjustMutation(iter);

            if (iter < (ITERATIONS - (FEASIBLELIMIT * ITERATIONS))) {
                checkFeasible = true;
            }
        }
        System.out.println("Distance: " + Validation.validate(c.get(0).getResultValue(), FTYPE));
        return c.get(0);
    }

    /**
     * Start the evolution.
     * This method uses mutation and crossover operations.
     * @param pop		(number of population members => 2^x)
     * @param part_pec 	(percentage for partitioning)
     * @return
     */
    public SolutionCandidate evolveOR(int pop, double part_perc) {

        // init population (first generation)
        ArrayList<SolutionCandidate> c = initPopulation(pop);
        Collections.sort(c);

        int iter = ITERATIONS;

        // calculate partition index
        int part_index = (int) (pop * part_perc);

        // evolution
        while (iter > 0) {
            int i = part_index;

            while (i < pop) {
                for (int j = 0; j < part_index; j++) {

                    SolutionCandidate sc;

                    // Find random value of top x percent and mate'n'mutate
                    int rand = (int) (Math.random() * part_index);

                    sc = mateMultiPointXover(c.get(j), c.get(rand));
                    // sc = mateAvg(c.get(j), c.get(rand));
                    
                    if (Math.random() < MUTATION) {
                        sc = mutateRandom(sc, MUTATIONS_PER_CREATURE);
                        // sc = mutateSwitchN(sc, MUTATIONS_PER_CREATURE);
                        // sc = mateSinglePointXover(c.get(j), c.get(rand));
                    }
                    // sc = mutateRandomVariable(sc);

                    c.set(i, sc);
                    if (++i >= pop) break;
                }
            }

            System.out.println("Iteration: " + iter);

            c = RabbitMqClient.getInstance().sendAndWaitForResult(c, pop, checkFeasible);
            Collections.sort(c);

            if (c.get(0).getResultValue() == c.get(pop - 1).getResultValue()) {
                Collections.shuffle(c);
            }

            System.out.println("Current top value: " + c.get(0).getResultValue() + " (" + c.get(0).isFeasible() + ")");
            iter--;
            adjustMutation(iter);

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

        for (int i = 0; i < pop; i++) {
            // random vector x
            ArrayList<Double> t = new ArrayList<>();
            for (int j = 0; j < DIMENSION; j++) {
                t.add((Math.random() * 2));
            }
            
            c.add(new SolutionCandidate(t));
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
        for (int i = 0; i < mateOne.getSolutionVector().size(); i++) {
            x.add(
	            (mateOne.getSolutionVector().get(i) * mateTwo.getResultValue()
                + mateTwo.getSolutionVector().get(i) * mateOne.getResultValue())
                / (mateTwo.getResultValue() + mateOne.getResultValue())
            );
        }
        return new SolutionCandidate(x);
    }

    /**
     * Mate two candidates using a single point crossover strategy.
     * @param mateOne
     * @param mateTwo
     * @return
     */
    private SolutionCandidate mateSinglePointXover(SolutionCandidate mateOne, SolutionCandidate mateTwo) {
        int partitionIdx = (int) (Math.random() * DIMENSION);
        if (partitionIdx == 0 || partitionIdx == 16) partitionIdx = (int) (DIMENSION / 2);

        ArrayList<Double> p1 = new ArrayList<>();
        for (Double d : mateOne.getSolutionVector()) {
            p1.add(d);
        }

        ArrayList<Double> p2 = new ArrayList<>();
        for (Double d : mateTwo.getSolutionVector()) {
            p2.add(d);
        }

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
     * Mate two candidates using a multi point crossover strategy.
     * @param mateOne
     * @param mateTwo
     * @return
     */
    private SolutionCandidate mateMultiPointXover(SolutionCandidate mateOne, SolutionCandidate mateTwo) {
        ArrayList<Double> v1 = mateOne.getSolutionVector();
        ArrayList<Double> v2 = mateTwo.getSolutionVector();
        ArrayList<Double> result = new ArrayList<>();

        for (int i = 0; i < DIMENSION; i++) {
            if (r.nextBoolean()) {
                result.add(v1.get(i));
            } else {
                result.add(v2.get(i));
            }
        }
        
        return new SolutionCandidate(result);
    }

    /**
     * Mutate by using gaussian random numbers.
     * @param c			(solution candidate)
     * @param n			(number of elements to be mutated)
     * @return
     */
    private SolutionCandidate mutateGauss(SolutionCandidate c, int n) {
        ArrayList<Double> t = c.getSolutionVector();
        ArrayList<Double> nw = new ArrayList<>();

        for (Double d : t) nw.add(d);

        // generate n uniformly distributed random numbers
        List<Integer> pos = Stream
	        .generate(() -> (int) (Math.random() * DIMENSION))
	        .distinct()
	        .limit(n)
	        .collect(Collectors.toList());

        for (int i = 0; i < pos.size(); i++) {
            int idx = pos.get(i);
            double newValue = t.get(idx) + next_gaussian();
            
            if (newValue > UPPERLIMIT / 2) {
                newValue = UPPERLIMIT;
            } else if (newValue < 0) {
                newValue = 0;
            }
            
            nw.set(idx, newValue);
        }

        return new SolutionCandidate(nw);
    }

    /**
     * Mutate by switching elements.
     * @param c (solution candidate)
     * @param n (number of elements to be switched)
     */
    public SolutionCandidate mutateSwitchN(SolutionCandidate c, int n) {
        ArrayList<Double> t = c.getSolutionVector();
        ArrayList<Double> nw = new ArrayList<>();

        for (Double d : t) nw.add(d);

        // generate n uniformly distributed random numbers
        List<Integer> pos = Stream
            .generate(() -> (int) (Math.random() * DIMENSION))
            .distinct()
            .limit(n)
            .collect(Collectors.toList());

        // switch positions
        for (int i = 0; i < pos.size(); i += 2) {
            int idx1 = pos.get(i);
            int idx2 = pos.get(i + 1);
            double pos1 = t.get(idx1);

            t.set(idx1, t.get(idx2));
            t.set(idx2, pos1);
        }

        return new SolutionCandidate(nw);
    }

    /**
     * Mutate by using uniformly distributed random numbers.
     * The number of mutations has to be specified.
     * @param c (solution candidate)
     * @param n (number of elements to be mutated)
     * @return
     */
    private SolutionCandidate mutateRandom(SolutionCandidate c, int n) {
        ArrayList<Double> t = c.getSolutionVector();
        ArrayList<Double> nw = new ArrayList<>();

        for (Double d : t) nw.add(d);

        // generate n uniformly distributed random numbers
        List<Integer> pos = Stream
            .generate(() -> (int) (Math.random() * DIMENSION))
            .distinct()
            .limit(n)
            .collect(Collectors.toList());

        // generate n uniformly distributed random numbers
        List<Double> val = Stream
            .generate(() -> ((Math.random() * UPPERLIMIT)))
            .distinct()
            .limit(n)
            .collect(Collectors.toList());

        for (int i = 0; i < pos.size(); i++) {
            nw.set(pos.get(i), val.get(i));
        }
        return new SolutionCandidate(nw);
    }

    /**
     * Mutate by using uniformly distributed random numbers.
     * The number of mutations does not have to be specified.
     * @param c (solution candidate)
     * @return
     */
    private SolutionCandidate mutateRandomVariable(SolutionCandidate c) {
        ArrayList<Double> t = c.getSolutionVector();
        ArrayList<Double> nw = new ArrayList<>();

        for (Double d : t) nw.add(d);

        for (int i = 0; i < nw.size(); i++) {
            if (Math.random() < MUTATION_RATE) {
                nw.set(i, (Math.random() * UPPERLIMIT));
            }
        }
        return new SolutionCandidate(nw);
    }

    /**
     * Gaussian random numbers.
     * @return
     */
    private double next_gaussian() {
        // generate an initial [-1,1] gaussian distribution
        // quantize to step size 0.001
        return Math.rint((r.nextGaussian()) * 1000.0) * 0.001;
    }

    /**
     * Change mutation rate over time.
     * @param iter
     */
    private void adjustMutation(int iter) {
        // this funtion is optimized for iter = 1000
        Double value = exp((-((ITERATIONS - iter) * 0.0009) - 1));
        System.out.println("Mutation rate: " + value);
        MUTATION = value;
    }

	/*
	 * Local test functions.
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