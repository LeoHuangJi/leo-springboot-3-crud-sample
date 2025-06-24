/**
 * 
 */
package vn.leoo.common.queue;

public enum JmsType {
	TEXT_MESSAGE("TextMessage"),
	BYTES_MESSAGE("BytesMessage");	
	
    private final String value;

    private JmsType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.value;
    }

    public String getName(){
        return this.name();
    }

    public static JmsType getMsgType(String value) {
        for (JmsType a : values()) {
            if (a.value.equalsIgnoreCase(value)) {
                return a;
            }
        }
        return null;
    }   
}
