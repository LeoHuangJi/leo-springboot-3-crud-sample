package vn.leoo.common.config.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleByAppCodeDTO implements Serializable {
    List<RoleByAppCodeItemDTO> authorities;
    List<RoleByAppCodeItemDTO> roles;
}
