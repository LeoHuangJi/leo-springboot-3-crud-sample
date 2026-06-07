package vn.leoo.shopli.dto.dlsfilter.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterOperatorConfig {

    private String key;

    private String label;

    private String sql;

    private String transform;

    private Boolean multiValue;

}