package exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EmitLogTopic {
	
	/*
	 Topic exchange
	 Topic exchange非常强大，可以实现其他任意路由器的功能。
	 当一个队列以绑定键#绑定，它将会接收到所有的消息，而无视路由键（实际是绑定键#匹配了任意的路由键）。----这和fanout路由器一样了。
	 当*和#这两个特殊的字符不出现在绑定键中，Topic exchange就会和direct exchange类似了。
	 */

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            //建立连接和通道
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();

            //声明路由器和路由器类型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            //定义路由键和消息

//            String routingKey = "kern.critical";
//            String message = "A kern critical error.....";
            
//            String routingKey = "kern.info";
//            String message = "A kern info error.....";

//            String routingKey = "kern.warn";
//            String message = "A kern warn  error.....";

            String routingKey = "cron.warn";
            String message = "A cron warn error.....";

            //发布消息
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
