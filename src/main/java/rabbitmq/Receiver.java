package rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Class Receiver.
 * @author Daniel, Alex
 *
 */
public class Receiver {
	
	private Connector connector;
	
	/**
	 * Constructor.
	 */
	public Receiver() {
		connector = Connector.getInstance();
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
					System.out.println("Message: " + message);
				}
		    };
		    
		    connector.getChannel().basicConsume(Connector.OUTQUEUE, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
