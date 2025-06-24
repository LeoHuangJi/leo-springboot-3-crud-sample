package vn.leoo.common.config.dto;

import lombok.Data;
import java.util.List;

@Data
public class PermissionResponse {
    private String code;
    private String message;
    private String status;
    private RoleByAppCodeDTO data  ;
}
