package vn.leoo.shopli.dto.dlsfilter.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterFieldResponse {

    private String field;

    private String label;

    private String type;

    private String component;

    private ValidationResponse validation;

    private List<FilterOperatorResponse> operators;



}