package rabbitmq;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import algorithms.DataContainer.SolutionCandidate;

/**
 * Class Sender.
 * Sends messages to RabbitMQ.
 * @author Daniel, Alex
 *
 */
public class Sender {
	private Connector connector;
	private Gson gson;
	
	/**
	 * Constructor.
	 */
	public Sender() {
		// get instance of rabbitmq connector
		connector = Connector.getInstance();
		gson = new Gson();
	}

	/**
	 * Send paramters to RabbitMQ.
	 * @param solutionCandidates
	 * @return
	 */
	public void send(ArrayList<SolutionCandidate> solutionCandidates) {
		try {
			connector.getChannel().queueDeclare(Connector.INQUEUE, false, false, false, null);
			
			for(SolutionCandidate c : solutionCandidates) {
				String message = createMsg(c);
				connector.getChannel().basicPublish("", Connector.INQUEUE, null, message.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create JSON object for message.
	 * @param c
	 * @return
	 */
	private String createMsg(SolutionCandidate c){
		String msg = gson.toJson(c);
		return msg;
	}	
}
