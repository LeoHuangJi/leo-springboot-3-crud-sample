package vn.leoo.shopli.dto.dlsfilter.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterOperatorResponse {

    private String key;

    private String label;

    private Boolean multiValue;

}