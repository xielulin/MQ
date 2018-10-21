package com.xll.mq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

 /** 主题模式
 * @ClassName: TopicConsumer
 * @Description:消息接收者 
 * @author: XieLulin
 * @date: 2018年3月12日 下午10:12:11  
 */
public class TopicConsumer {
     //mq url地址
     private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
     //主题名
     private static final String TOPIC_NAME = "firstTopic";

	public static void main(String[] args) {
		// 1.创建ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);

		// 2. 创建Connection
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();

			// 3.启动连接
			connection.start();

			// 4.创建会话
			//第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
			//第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
			//Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
			//Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
			//DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// 5.创建一个目标
			Destination destination = session.createTopic(TOPIC_NAME);

			// 6.创建一个消费者
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("接收到的Topic消息:"+textMessage.getText());
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
