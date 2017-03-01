package rabbitmq;

import java.io.IOException;
import java.util.UUID;
import java.util.Vector;

import org.json.JSONObject;

/**
 * Class Sender.
 * Sends messages to RabbitMQ.
 * @author Daniel, Alex
 *
 */
public class Sender {
	
	private Connector connector;
	
	/**
	 * Constructor.
	 */
	public Sender() {
		// get instance of rabbitmq connector
		connector = Connector.getInstance();
	}

	/**
	 * Send paramters to RabbitMQ.
	 * @param x 
	 * @return
	 */
	public void send(Vector<Double> x){
		String id = UUID.randomUUID().toString();
		String message = createMsg(id, x);
		try {
			connector.getChannel().queueDeclare(Connector.INQUEUE, false, false, false, null);
			connector.getChannel().basicPublish("", Connector.INQUEUE, null, message.getBytes());
			System.out.println("Sent: " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create JSON object for message.
	 * @param id
	 * @param parameters
	 * @return
	 */
	private static String createMsg(String id, Vector<Double> parameters){
		JSONObject obj = new JSONObject();
		obj.put("solutionCandidateId", id);
		obj.put("solutionVector", parameters);
		String msg = obj.toString();
		return msg;
	}	
}
