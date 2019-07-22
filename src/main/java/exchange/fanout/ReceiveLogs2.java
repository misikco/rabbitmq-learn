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

public class ReceiveLogs2 {
	
	private static final String EXCHANGE_NAME = "logs";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//建立连接和通道
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		Connection newConnection = connectionFactory.newConnection();
		Channel createChannel = newConnection.createChannel();
		
		//声明路由器及类型
		createChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		
		//声明一个随机名字的队列
		String queuename = createChannel.queueDeclare().getQueue();
		
		//绑定队列到路由器上
		createChannel.queueBind( queuename,EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        
        //开始监听消息
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
