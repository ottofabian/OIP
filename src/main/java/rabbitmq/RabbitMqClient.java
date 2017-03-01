package rabbitmq;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import algorithms.SolutionCandidate;

/**
 * Class RabbitMqClient.
 * Encapsulates functionality to communicate
 * with the RabbitMQ queue.
 * @author Daniel
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
	 * @param c
	 * @param pop
	 * @return
	 */
	public ArrayList<SolutionCandidate> sendAndWaitForResult(ArrayList<SolutionCandidate> c, int pop) {
		new Sender().send(c);
		
		CountDownLatch latch = new CountDownLatch(1);
		Receiver receiver = new Receiver(latch, pop);
		new Thread(receiver).start();
		
		try {
			// block call, wait for receiver
			latch.await();
		} catch (InterruptedException e) {}
		
		System.out.println("Received package");
		return receiver.getResults();
	}
}
