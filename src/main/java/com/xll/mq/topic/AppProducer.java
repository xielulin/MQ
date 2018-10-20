package com.xll.mq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**主题模式
 * @ClassName: AppProducer
 * @Description: 消息发送者
 * @author: XieLulin
 * @date: 2018年3月12日 下午9:03:16
 */
public class AppProducer {
	private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private static final String urls = "tcp://192.168.1.106:61616";
	private static final String topicName = "firtTopic";

	public static void main(String[] args) {
		// 1.创建ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

		// 2. 创建Connection
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();

			// 3.启动连接
			connection.start();

			// 4.创建会话
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// 5.创建一个目标
			Destination destination = session.createTopic(topicName);

			// 6.创建一个生产者
			MessageProducer producer = session.createProducer(destination);

			for (int i = 0; i < 100; i++) {
				// 7.创建消息
				TextMessage message = session.createTextMessage("text:" + i);
				producer.send(message);
				System.out.println("发送消息：" + message.getText());
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 9.关闭连接
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
