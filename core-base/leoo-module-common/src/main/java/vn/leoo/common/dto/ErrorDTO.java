package vn.leoo.common.dto;


public class ErrorDTO extends BaseDTO{
	private static final long serialVersionUID = -8061539243264650022L;
	private Object error;

	public ErrorDTO(){
		
	}
	
	public ErrorDTO(Object error){
		this.error = error;
	}
	
	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
}
