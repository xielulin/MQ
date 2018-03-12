package com.xll.mq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

 /** 队列模式
 * @ClassName: AppConsumer 
 * @Description:消息接收者 
 * @author: XieLulin
 * @date: 2018年3月12日 下午10:12:11  
 */
public class AppConsumer {

	private static final String url = "tcp://192.168.1.106:61616";
	private static final String queueName = "firtQueue";

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
			Destination destination = session.createQueue(queueName);

			// 6.创建一个消费者
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("接收到的message:"+textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

		} catch (JMSException e) {
			e.printStackTrace();
		}  

	}

}
