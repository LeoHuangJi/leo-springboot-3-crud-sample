package vn.leoo.shopli.dto.dlsfilter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DynamicFilterRequest {

    private List<FilterCondition> conditions;

    private Integer page = 0;

    private Integer size = 20;

}