package rabbitmq;

import algorithms.datacontainer.SolutionCandidate;
import com.google.gson.Gson;

import java.util.ArrayList;

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
        //int stop = 0;
        try {
			connector.getChannel().queueDeclare(Connector.INQUEUE, false, false, false, null);
			
			for(SolutionCandidate c : solutionCandidates) {
				String message = createMsg(c);
				connector.getChannel().basicPublish("", Connector.INQUEUE, null, message.getBytes());
                /*stop++;
				if(stop == 15900){
					sleep(2000);
					stop = 0;
				}*/
			}
        } catch (Exception e) {
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
