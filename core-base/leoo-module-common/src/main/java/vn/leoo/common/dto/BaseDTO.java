package vn.leoo.common.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

public class BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void copyDataTo(Object destination){
        BeanUtils.copyProperties(this, destination);
    }
	
	public void copyDataFrom(Object source){
        BeanUtils.copyProperties(source, this);
    }

	
}
