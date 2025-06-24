package vn.leoo.common.config.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleByAppCodeItemDTO implements Serializable {
    @Column(name = "MA_QUYEN")
    private String permissionCode;
    @Column(name = "PHUONG_THUC")
    private String method;
    @Column(name = "DUONG_DAN")
    private String endpoint;
}
