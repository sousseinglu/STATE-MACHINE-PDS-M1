package model;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public void sendMessage() throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // JMS messages are sent and received using a Session. We will
        // create here a non-transactional session object. If you want
        // to use transactions you should set the first parameter to 'true'
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("testpds");

        MessageProducer producer = session.createProducer(queue);

        // We will send a small text message saying 'Hello'

        TextMessage message = session.createTextMessage();

        message.setText("State machine message");
        // Here we are sending the message!
        producer.send(message);
        System.out.println("Sent message '" + message.getText() + "'");

        connection.close();
    }
}
