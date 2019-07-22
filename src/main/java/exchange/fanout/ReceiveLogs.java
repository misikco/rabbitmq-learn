package exchange.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class ReceiveLogs {
	
	private static final String EXCHANGE_NAME = "logs";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		Connection newConnection = connectionFactory.newConnection();
		Channel createChannel = newConnection.createChannel();
		
		//声明路由以及路由的类型
		createChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		
		String queuename = createChannel.queueDeclare().getQueue();
		
		createChannel.queueBind( queuename,EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        
		DefaultConsumer defaultConsumer = new DefaultConsumer(createChannel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body,"UTF-8");
                System.out.println(" [x] Received '" + message + "'");
			}
		};
		
		createChannel.basicConsume(queuename, true, defaultConsumer);
		
		
	}

}
