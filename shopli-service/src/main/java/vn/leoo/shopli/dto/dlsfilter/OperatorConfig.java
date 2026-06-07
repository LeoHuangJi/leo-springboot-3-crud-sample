package vn.leoo.shopli.dto.dlsfilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorConfig {

    private String key;

    private String sql;

    private String transform;

    private Boolean multiValue;

}
