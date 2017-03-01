package rabbitmq;

import java.util.Vector;

public class Test {
	public static void main(String[] args) {
		Sender sender = new Sender();
		Receiver receiver = new Receiver();
		
		Vector<Double> x = new Vector<>();
		x.add(1.0); x.add(1.0);
		x.add(1.0); x.add(1.0);
		x.add(1.0); x.add(1.0);
		x.add(1.0); x.add(1.0);
		x.add(1.0); x.add(1.0);
		x.add(1.0); x.add(1.0);
		x.add(1.0); x.add(1.0);
		x.add(1.0); x.add(1.0);
		x.add(1.0);
		
		sender.send(x);
		receiver.receive();
		//rmqc.close();
	}
}
