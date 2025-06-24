package vn.leoo.common.dto;

public class InformationDTO extends BaseDTO{
	private static final long serialVersionUID = 334794780764958176L;

	private boolean status;	
	private String message;
	private String resourceKey;
	private Object content;		

	public InformationDTO(){
		
	}
	
	public InformationDTO(boolean status, Object content){
		this.status = status;
		this.content = content;
	}
	
	public InformationDTO(boolean status, String message, String resourceKey){
		this.status = status;
		this.message = message;
		this.resourceKey = resourceKey;
	}


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
}
