/*
 * package vn.leoo.common.queue;
 * 
 * import java.io.ByteArrayInputStream; import java.io.StringWriter;
 * 
 * import javax.jms.BytesMessage; import javax.jms.Connection; import
 * javax.jms.Destination; import javax.jms.JMSConsumer; import
 * javax.jms.JMSContext; import javax.jms.JMSException; import
 * javax.jms.JMSProducer; import javax.jms.Message; import
 * javax.jms.MessageConsumer; import javax.jms.MessageProducer; import
 * javax.jms.Session; import javax.jms.TextMessage; import
 * javax.xml.transform.Transformer; import
 * javax.xml.transform.TransformerFactory; import
 * javax.xml.transform.stream.StreamResult; import
 * javax.xml.transform.stream.StreamSource;
 * 
 * import org.springframework.core.env.Environment;
 * 
 * import vn.leoo.common.constants.Constants;
 * 
 * public class JmsServiceImpl implements JmsService{ // System exit status
 * value (assume unset value to be 1) int status = 1;
 * 
 * // Create variables for the connection to MQ String HOST = "_YOUR_HOSTNAME_";
 * // Host name or IP address int PORT = 1414; // Listener port for your queue
 * manager String CHANNEL = "DEV.APP.SVRCONN"; // Channel name String QMGR =
 * "QM1"; // Queue manager name String APP_USER = "app"; // User name that
 * application uses to connect to MQ String APP_PASSWORD = "_APP_PASSWORD_"; //
 * Password that the application uses to connect to MQ String QUEUE_IN =
 * "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to
 * and from String QUEUE_OUT = "DEV.QUEUE.1"; // Queue that the application uses
 * to put and get messages to and from String CONNECTION_FACTORY_NAME;
 * 
 * long t; String sender_code;
 * 
 * JMSContext context = null; Destination destination = null; JMSProducer
 * jProducer = null; JMSConsumer jConsumer = null; JmsConnectionFactory
 * jmsConnFactory = null;
 * 
 * Session session = null; Connection conn = null; MessageProducer mProducer =
 * null; MessageConsumer mConsumer = null;
 * 
 * public JmsServiceImpl(Environment env){ HOST =
 * env.getProperty(Constants.ESB_HOST).trim(); PORT =
 * Integer.parseInt(env.getProperty(Constants.ESB_PORT).trim()); CHANNEL =
 * env.getProperty(Constants.ESB_CHANNEL).trim(); QMGR =
 * env.getProperty(Constants.ESB_QUEUE_MANAGER).trim(); QUEUE_IN =
 * env.getProperty(Constants.ESB_QUEUE_IN).trim(); QUEUE_OUT =
 * env.getProperty(Constants.ESB_QUEUE_OUT).trim(); //CONNECTION_FACTORY_NAME =
 * 
 * t = Long.parseLong(env.getProperty(Constants.ESB_WAIT_INTERVAL).trim());
 * sender_code = env.getProperty(Constants.ESB_SENDER_CODE).trim(); }
 * 
 * // Create a connection factory public void createConnectionFactory(String
 * connFactoryName){ try{ switch (connFactoryName) { case "IBM": jmsConnFactory
 * = IbmConnectionFactory.getInstance(HOST, PORT, CHANNEL, QMGR, "DMDC");
 * jmsConnFactory.createConnectionFactory(); break;
 * 
 * default: System.out.println("Chạy vào khối default"); break; }
 * }catch(Exception e){ e.printStackTrace(); } }
 * 
 * //========= Using JMSProducer, JMSConsumer put and get message to queue
 * =========// public void createJMSProducer() throws Exception{ try{ //
 * this.createConnectionFactory(); context = jmsConnFactory.createContext();
 * destination = context.createQueue("queue:///" + QUEUE_IN); jProducer =
 * context.createProducer(); }catch(Exception e){ e.printStackTrace(); throw e;
 * } }
 * 
 * 
 * public void createJMSConsumer() throws Exception{ try{ //
 * this.createConnectionFactory(); context =
 * jmsConnFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE); destination =
 * context.createQueue("queue:///" + QUEUE_OUT); jConsumer =
 * context.createConsumer(destination); // autoclosable }catch(Exception e){
 * e.printStackTrace(); throw e; } }
 * 
 * public void sendByJP(String message) throws Exception{ try{
 * jProducer.send(destination, message); }catch(Exception e){
 * e.printStackTrace(); throw e; } }
 * 
 * public String receiveByJC() throws Exception{ String receivedMessage = "";
 * try{ receivedMessage = jConsumer.receiveBody(String.class, t);
 * }catch(Exception e){ e.printStackTrace(); throw e; } return receivedMessage;
 * }
 * 
 *//**
	 * Record this run as successful.
	 */
/*
 * @SuppressWarnings("unused") private void recordSuccess() {
 * System.out.println("SUCCESS"); status = 0; return; }
 * 
 *//**
	 * Record this run as failure.
	 *
	 * @param ex
	 */
/*
 * @SuppressWarnings("unused") private void recordFailure(Exception ex) { if (ex
 * != null) { if (ex instanceof JMSException) {
 * processJMSException((JMSException) ex); } else { System.out.println(ex); } }
 * System.out.println("FAILURE"); status = -1; return; }
 * 
 *//**
	 * Process a JMSException and any associated inner exceptions.
	 *
	 * @param jmsex
	 *//*
		 * private void processJMSException(JMSException jmsex) {
		 * System.out.println(jmsex); Throwable innerException =
		 * jmsex.getLinkedException(); if (innerException != null) {
		 * System.out.println("Inner exception(s):"); } while (innerException != null) {
		 * System.out.println(innerException); innerException =
		 * innerException.getCause(); } return; }
		 * 
		 * //========= Using Connection, Session, MessageProducer, MessageConsumer put
		 * and get message to queue =========// public void createMessageProducer()
		 * throws Exception{ try{ // this.createConnectionFactory(); // Create the
		 * connection conn = jmsConnFactory.createConnection(); // Create a session
		 * session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE); // Create a
		 * queue destination = session.createQueue("queue:///" + QUEUE_IN); // Create
		 * producer mProducer = session.createProducer(destination); //
		 * mProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // Start connection
		 * conn.start();
		 * 
		 * }catch(Exception e){ e.printStackTrace(); throw e; } }
		 * 
		 * public void createMessageConsumer() throws Exception{ try{ //
		 * this.createConnectionFactory(); // Create the connection conn =
		 * jmsConnFactory.createConnection(); // Create a session session =
		 * conn.createSession(true, Session.SESSION_TRANSACTED); // Create a queue
		 * destination = session.createQueue("queue:///" + QUEUE_OUT); // Create
		 * consumer mConsumer = session.createConsumer(destination); // Start connection
		 * conn.start();
		 * 
		 * }catch(Exception e){ e.printStackTrace(); throw e; } }
		 * 
		 * public void sendByMP(String message, JmsType jmsType) throws Exception{ try{
		 * String msgType = jmsType.getValue();
		 * if(msgType.equals(JmsType.TEXT_MESSAGE.getValue())){ TextMessage msg =
		 * session.createTextMessage(); msg.setText(message); mProducer.send(msg); }else
		 * if(msgType.equals(JmsType.BYTES_MESSAGE.getValue())){ byte[] uselessData =
		 * message.getBytes(); BytesMessage msg = session.createBytesMessage();
		 * msg.writeBytes(uselessData); mProducer.send(msg); } }catch(Exception e){
		 * e.printStackTrace(); throw e; } }
		 * 
		 * public String receiveByMC() throws Exception{ String receivedMessage = "";
		 * try{ Message receivedMsg = mConsumer.receiveNoWait(); receivedMessage =
		 * this.processMessage(receivedMsg); }catch(Exception e){ e.printStackTrace();
		 * throw e; } return receivedMessage; }
		 * 
		 * public void commit() throws Exception{ if(session != null) session.commit();
		 * }
		 * 
		 * public void rollback() throws Exception{ if(session != null)
		 * session.rollback(); }
		 * 
		 * public void release() throws Exception{ if(jConsumer != null){
		 * jConsumer.close(); } if(mConsumer != null){ mConsumer.close(); } if(mProducer
		 * != null){ mProducer.close(); } if(session != null){ session.close(); }
		 * if(conn != null){ conn.stop(); conn.close(); } jmsConnFactory.clear(); }
		 * 
		 * private String processMessage(Message receivedMsg) throws Exception { String
		 * txt = ""; String messageId = ""; int deliveryCount = 0;
		 * 
		 * if (receivedMsg instanceof TextMessage) { TextMessage rcvTxtMsg =
		 * (TextMessage) receivedMsg; messageId = rcvTxtMsg.getJMSMessageID();
		 * deliveryCount =
		 * receivedMsg.getIntProperty("JMSXDeliveryCount");//(MQPropertyIdentifiers.
		 * MQ_JMSX_DELIVERY_COUNT); StreamSource source = new StreamSource(new
		 * ByteArrayInputStream(rcvTxtMsg.getBody(String.class).getBytes("UTF-8"))); txt
		 * = this.getStringFrom(source); // txt = rcvTxtMsg.getText(); } else if
		 * (receivedMsg instanceof BytesMessage) { BytesMessage rcvBytesMsg =
		 * (BytesMessage)receivedMsg; messageId = rcvBytesMsg.getJMSMessageID();
		 * deliveryCount =
		 * rcvBytesMsg.getIntProperty("JMSXDeliveryCount");//(MQPropertyIdentifiers.
		 * MQ_JMSX_DELIVERY_COUNT); byte data[] = new
		 * byte[(int)rcvBytesMsg.getBodyLength()]; rcvBytesMsg.readBytes(data); txt =
		 * new String(data, "UTF-8"); }
		 * 
		 * // Check whether this message has previously failed processing. if
		 * (deliveryCount > 1) { System.out.println("WARNING: Message " + messageId +
		 * " has previously failed processing " + (deliveryCount - 1) + " times."); }
		 * 
		 * if (deliveryCount < 3) { // For testing purposes, simulate a poison message
		 * if the message we have // received // contains this specific text. if
		 * (txt.contains("POISON MESSAGE!")) { throw new
		 * RuntimeException("Simulated failure triggered!"); } //
		 * System.out.println("Successfully processed message " + messageId + " text=" +
		 * txt); System.out.println("Successfully processed message " + messageId); }
		 * else { // The message has failed processing multiple times over a sustained
		 * // period of time so is potentially a "poison" message, or else there is // a
		 * non-transient problem with the business processing. // // To avoid the
		 * message blocking the queue indefinitely you can decide // to implement some
		 * alternative processing here.
		 * 
		 * // In this case we let the method complete successfully without doing any //
		 * processing, which effectively causes it to be discarded. //
		 * System.out.println("WARNING: Discarding poison message " + messageId +
		 * " text=" + txt); System.out.println("WARNING: Discarding poison message " +
		 * messageId); } return txt; }
		 * 
		 * private String getStringFrom(StreamSource source) throws Exception{ String
		 * strResult = ""; try { StringWriter writer = new StringWriter(); StreamResult
		 * result = new StreamResult(writer); TransformerFactory tFactory =
		 * TransformerFactory.newInstance(); Transformer transformer =
		 * tFactory.newTransformer(); transformer.transform(source,result); strResult =
		 * writer.toString(); } catch (Exception e) { e.printStackTrace(); throw e; }
		 * return strResult; } }
		 */