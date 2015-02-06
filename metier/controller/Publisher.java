package controller;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServlet;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Servlet implementation class Publisher
 */
public class Publisher extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String url = "tcp://localhost:61616";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Publisher() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    // sends message to a destination
    // takes String destination and String message as param
    public void sendMessage(String destination, String msg){
    	try { 
    	 ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
         Connection connection = connectionFactory.createConnection();
         connection.start();

       
         Session session = connection.createSession(false,
                 Session.AUTO_ACKNOWLEDGE);
        
         
         Destination queuePersistance = session.createQueue("testpdsPersistance");
         Destination queuePresentation = session.createQueue("testpdsPresentation");

         MessageProducer producerPersistance = session.createProducer(queuePersistance);
         MessageProducer producerPresentation = session.createProducer(queuePresentation);

         TextMessage message = session.createTextMessage();

         message.setText(msg);
         // Checks the destination and sends message
         if(destination.equals("persistance")){
        	 producerPersistance.send(message);
        	 System.out.println("Sent message to persistance'" + message.getText() + "'");
         }
         else if(destination.equals("presentation")){
        	 producerPresentation.send(message);
        	 System.out.println("Sent message to presentation '" + message.getText() + "'");
         }
         

         
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
