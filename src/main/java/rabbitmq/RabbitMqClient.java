package rabbitmq;

import algorithms.datacontainer.SolutionCandidate;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Class RabbitMqClient.
 * Encapsulates functionality to communicate
 * with the RabbitMQ queue.
 * @author Alex, Daniel, Fabian
 *
 */
public class RabbitMqClient {
	private final static RabbitMqClient instance = new RabbitMqClient();
	
	/**
	 * Private constructor.
	 */
	private RabbitMqClient() {}
	
	/**
	 * Get singleton instance of rabbitmq client.
	 * @return
	 */
	public static RabbitMqClient getInstance() {
		return instance;
	}
	
	/**
	 * Send solution candidates
	 * to RabbitMQ and wait for result.
	 * Increase result value if the value is not feasible.
	 * @param c		(solution candidate)
	 * @param pop	(population)
	 * @param checkFeasible
	 * @return
	 */
	public ArrayList<SolutionCandidate> sendAndWaitForResult(ArrayList<SolutionCandidate> c, int pop, boolean checkFeasible) {
		new Sender().send(c);
		
		CountDownLatch latch = new CountDownLatch(1);
		Receiver receiver = new Receiver(latch, pop);
		new Thread(receiver).start();
		
		try {
			// block call, wait for receiver
			latch.await();
		} catch (InterruptedException e) {}

		//System.out.println("Received package");
		ArrayList<SolutionCandidate> list = receiver.getResults();

		if (checkFeasible) {
			for (int i = 0; i < list.size(); i++) {
				SolutionCandidate elem = list.get(i);
				if (!elem.isFeasible()) {
					elem.setResultValue(1000000);
				}
			}
		}
		return list;
	}
}
