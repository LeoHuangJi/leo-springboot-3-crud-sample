package vn.leoo.common.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAppDTO implements Serializable {
    private String name;
    private String code;
    private String url;
    private String logo;
    private Integer order;
}
