package com.zzk.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class TaskSend {
	private final static String QUEUE_NAME = "auto task";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			channel.confirmSelect();
			channel.addConfirmListener(new ConfirmListener() {
				@Override
				public void handleNack(long l, boolean flag) throws IOException {
					System.out.println("handleNack");
				}
				
				@Override
				public void handleAck(long l, boolean flag) throws IOException {
					System.out.println(l);
					System.out.println(flag);
				}
			});
			for (int i = 1; i < 100; i++) {
				String message = i+"";
				for (int j = 0; j < i; j++) {
					message += ".";
				}
				channel.basicPublish( "", QUEUE_NAME,
			            MessageProperties.PERSISTENT_TEXT_PLAIN,
			            message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");
//				Thread.sleep(2000);
				
			}
			
			channel.waitForConfirms();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			channel.close();
//			connection.close();
		}
	}
}
