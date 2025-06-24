package vn.leoo.common.queue;

public interface JmsService {
	public void createConnectionFactory(String connFactoryName);
	
	public void createJMSProducer() throws Exception;
	
	public void createJMSConsumer() throws Exception;
	
	public void sendByJP(String message) throws Exception;
	
	public String receiveByJC() throws Exception;
	
	public void createMessageProducer() throws Exception;
	
	public void createMessageConsumer() throws Exception;
	
	public void sendByMP(String message, JmsType jmsType) throws Exception;
	
	public String receiveByMC() throws Exception;
	
	public void commit() throws Exception;
	
	public void rollback() throws Exception;
	
	public void release() throws Exception;
}
