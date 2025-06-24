package vn.leoo.common.data;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
    private String key;
    private Object value;
    private SearchOperation searchOperation;
    
    private boolean keyWord = false;
    private Class<?> searchClass;
    
    private List<String> propertyName = new ArrayList<>();
    private List<Object> propertyValue = new ArrayList<>();
    private List<SearchOperation> operation = new ArrayList<>();
    private List<SearchOperation> logicOperation = new ArrayList<>();
    private List<String> orderList = new ArrayList<>();
    private List<SearchOperation> orderOperation = new ArrayList<>();
    
    public SearchCriteria() {}
    
    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.searchOperation = operation;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public SearchOperation getSearchOperation() {
		return searchOperation;
	}

	public void setSearchOperation(SearchOperation searchOperation) {
		this.searchOperation = searchOperation;
	}

	public List<String> getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(List<String> propertyName) {
		this.propertyName = propertyName;
	}

	public List<Object> getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(List<Object> propertyValue) {
		this.propertyValue = propertyValue;
	}

	public List<SearchOperation> getOperation() {
		return operation;
	}

	public void setOperation(List<SearchOperation> operation) {
		this.operation = operation;
	}

	public List<SearchOperation> getLogicOperation() {
		return logicOperation;
	}

	public void setLogicOperation(List<SearchOperation> logicOperation) {
		this.logicOperation = logicOperation;
	}

	public List<String> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<String> orderList) {
		this.orderList = orderList;
	}

	public List<SearchOperation> getOrderOperation() {
		return orderOperation;
	}

	public void setOrderOperation(List<SearchOperation> orderOperation) {
		this.orderOperation = orderOperation;
	}

	public boolean isKeyWord() {
		return keyWord;
	}

	public void setKeyWord(boolean keyWord) {
		this.keyWord = keyWord;
	}

	public Class<?> getSearchClass() {
		return searchClass;
	}

	public void setSearchClass(Class<?> searchClass) {
		this.searchClass = searchClass;
	}
}
