package rabbitmq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import algorithms.DataContainer.SolutionCandidate;

/**
 * Class Receiver.
 * @author Daniel, Alex
 *
 */
public class Receiver implements Runnable {
	private Connector connector;
	private Gson gson;
	private CountDownLatch latch;
	private int length;
	
	// array list to store the results
	private ArrayList<SolutionCandidate> results = new ArrayList<>();
	
	/**
	 * Constructor.
	 * @param latch
	 * @param length
	 */
	public Receiver(CountDownLatch latch, int length) {
		connector = Connector.getInstance();
		gson = new Gson();
		
		this.latch = latch;
		this.length = length;
	}
	
	/**
	 * Receive messages from RabbitMQ.
	 * @return
	 */
	public void receive() {
		try {
			connector.getChannel().queueDeclare(Connector.OUTQUEUE, true, false, false, null);
			Consumer consumer = new DefaultConsumer(connector.getChannel()) {
				
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
					String message = new String(body, "UTF-8");
					results.add(gson.fromJson(message, SolutionCandidate.class));
					
					//check if all results have been received
					if(results.size() == length) {
						// disconnect receiver from queue
						connector.getChannel().basicCancel(this.getConsumerTag());
						// notify main thread
						latch.countDown();			
					}
				}
		    };
		    
		    connector.getChannel().basicConsume(Connector.OUTQUEUE, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		receive();
	}
	
	/**
	 * Get results.
	 * @return
	 */
	public ArrayList<SolutionCandidate> getResults() {
		return new ArrayList<>(results);
	}
}
