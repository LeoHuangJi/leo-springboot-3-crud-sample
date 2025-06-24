package vn.leoo.common.dto;


public class ResultDTO extends BaseDTO{
	private static final long serialVersionUID = -5756439810511077165L;

    private Object data;
    
    public ResultDTO(){
    	
    }
    
    public ResultDTO(Object data){
    	this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
