package vn.leoo.shopli.dto.dlsfilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterCondition {

    private String field;

    private String operator;

    private Object value;

}