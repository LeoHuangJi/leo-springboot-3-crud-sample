package vn.leoo.common.queue;

import javax.jms.Connection;
import javax.jms.JMSContext;

public abstract class JmsConnectionFactory {
	String host;	
	String channel;
	String qmgr;	
	String appName;
	Integer port;
	
	public abstract void createConnectionFactory() throws Exception;
	
	public abstract Connection createConnection() throws Exception;
	
	public abstract Connection createConnection(String username, String password) throws Exception;
	
	public abstract JMSContext createContext() throws Exception;
	
	public abstract JMSContext createContext(int sessionMode) throws Exception;
	
	public abstract void clear();
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getQmgr() {
		return qmgr;
	}

	public void setQmgr(String qmgr) {
		this.qmgr = qmgr;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
