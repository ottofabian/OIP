package rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Class Connector.
 * Connects to RabbitMQ
 * @author Daniel, Alex
 *
 */
public class Connector {
	private final static String HOST = "localhost";
	public final static String INQUEUE = "Inbound";
	public final static String OUTQUEUE = "Outbound";
	public final static String EXCHANGE = "results";
	
	private Connection connection;
	private Channel channel;
	
	private final static Connector instance = new Connector();

	/**
	 * Create RabbitMQ connector object.
	 */
	private Connector() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(HOST);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(OUTQUEUE, true, false, false, null);
			channel.queueBind(OUTQUEUE, EXCHANGE, "");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get singleton instance of connector.
	 * @return
	 */
	public static Connector getInstance() {
		return instance;
	}

	/**
	 * Close connection to RabbitMQ.
	 */
	public void close() {
		try {
			this.channel.close();
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get channel.
	 * @return
	 */
	public Channel getChannel() {
		return channel;
	}
}