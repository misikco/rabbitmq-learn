package com.zzk.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class DurableTaskSend {
	private final static String QUEUE_NAME = "durable task";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			boolean durable = true;
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
			for (int i = 1; i < 10; i++) {
				String message = i+"";
				for (int j = 0; j < i; j++) {
					message += ".";
				}
				channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
				System.out.println(" [x] Sent '" + message + "'");
			}
		} finally {
			channel.close();
			connection.close();
		}
	}
}
