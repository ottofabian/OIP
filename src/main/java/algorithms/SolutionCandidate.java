package algorithms;

import java.util.ArrayList;
import java.util.UUID;

public class SolutionCandidate implements Comparable<SolutionCandidate> {
	private String solutionCandidateId;
	private ArrayList<Double> solutionVector;
	private double resultValue = 1;
	private boolean isFeasible;
	private boolean isEvaluated;
	
	/**
	 * Constructor.
	 */
	public SolutionCandidate(ArrayList<Double> solutionVector) {
		solutionCandidateId = UUID.randomUUID().toString();
		this.solutionVector = solutionVector;
	}
	
	@Override
	public int compareTo(SolutionCandidate o) {
		return Double.compare(this.resultValue, o.getResultValue());
	}

	public String getSolutionCandidateId() {
		return solutionCandidateId;
	}

	public ArrayList<Double> getSolutionVector() {
		return solutionVector;
	}

	public double getResultValue() {
		return resultValue;
	}

	public boolean isFeasible() {
		return isFeasible;
	}

	public boolean isEvaluated() {
		return isEvaluated;
	}
}
