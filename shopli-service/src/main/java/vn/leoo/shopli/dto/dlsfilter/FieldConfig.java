package vn.leoo.shopli.dto.dlsfilter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FieldConfig {

    private String field;

    private String dbColumn;

    private String type;

    private List<OperatorConfig> operators;

    private ValidationConfig validation;

}
