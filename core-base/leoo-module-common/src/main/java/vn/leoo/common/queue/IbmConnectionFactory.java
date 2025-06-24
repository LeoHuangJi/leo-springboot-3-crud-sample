package vn.leoo.common.queue;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.Connection;
import javax.jms.JMSContext;

public class IbmConnectionFactory extends vn.leoo.common.queue.JmsConnectionFactory{
	static IbmConnectionFactory ibmConnFactory;
	
	JmsFactoryFactory ff;
	JmsConnectionFactory cf;
	
	public static IbmConnectionFactory getInstance(String host, Integer port, String channel, String qmgr, String appName){
		/*
		 * if(ibmConnFactory == null){ synchronized (IbmConnectionFactory.class) {
		 * if(ibmConnFactory == null){ ibmConnFactory = new IbmConnectionFactory(host,
		 * port, channel, qmgr, appName); } } }
		 */
		ibmConnFactory = new IbmConnectionFactory(host, port, channel, qmgr, appName);
		
		return ibmConnFactory;
	}
	
	private IbmConnectionFactory(String host, Integer port, String channel, String qmgr, String appName){
		this.host = host;
		this.port = port;
		this.channel = channel;
		this.qmgr = qmgr;
		this.appName = appName;
	}
	
	// Create a connection factory
	public void createConnectionFactory() throws Exception{
		ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		cf = ff.createConnectionFactory();
		
		// Set the properties
		cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
		cf.setIntProperty(WMQConstants.WMQ_PORT, port);
		cf.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, qmgr);
		cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, appName);
		cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, false);
		//cf.setStringProperty(WMQConstants.USERID, APP_USER);
		//cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);
	}
	
	public Connection createConnection() throws Exception{
		return cf.createConnection();
	}
	
	public Connection createConnection(String username, String password) throws Exception{
		return cf.createConnection(username, password);
	}
	
	public JMSContext createContext() throws Exception{
		return cf.createContext();
	}
	
	public JMSContext createContext(int sessionMode) throws Exception{
		return cf.createContext(sessionMode);
	}
	
	public void clear(){
		if(cf != null && !cf.isEmpty()){
			cf.clear();
		}
	}
}
