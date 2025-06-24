package vn.leoo.common.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract  class AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
  
    @Column(name = "ngay_Tao", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private Timestamp ngayTao=Timestamp.from(Instant.now());
    
    @Column(name = "nguoi_Tao", updatable = false)
    @JsonIgnore
    private String nguoiTao;  
    

    @Column(name = "ngay_Sua")
    @JsonIgnore
    private Timestamp ngaySua = Timestamp.from(Instant.now());
    
    @Column(name = "nguoi_Sua")
    @JsonIgnore
    private String nguoiSua;  
    
    @Column(name = "DELETED")
    @JsonIgnore
    private long deleted=0;
   

}
