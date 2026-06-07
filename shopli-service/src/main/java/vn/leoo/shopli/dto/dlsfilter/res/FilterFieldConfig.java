package vn.leoo.shopli.dto.dlsfilter.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterFieldConfig {

    private String field;

    private String label;

    private String dbColumn;

    private String type;

    private String component;

    private ValidationDTO validation;

    private List<FilterOperatorConfig> operators;


}