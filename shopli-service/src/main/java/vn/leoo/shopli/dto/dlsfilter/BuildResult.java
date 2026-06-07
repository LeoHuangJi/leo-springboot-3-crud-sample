package vn.leoo.shopli.dto.dlsfilter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class BuildResult {

    private String where;

    private Map<String,Object> params;

}