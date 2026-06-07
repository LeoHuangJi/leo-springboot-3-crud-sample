package vn.leoo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="CITIZEN")
@Getter
@Setter
public class CitizenEntity {

    @Id
    private String id;

    @Column(name="HO_TEN")
    private String hoTen;

    @Column(name="NAM_SINH")
    private Integer namSinh;

    @Column(name="GIOI_TINH")
    private String gioiTinh;

    @Column(name="SO_CCCD")
    private String soCCCD;

    @Column(name="SO_CMND")
    private String soCMND;

    @Column(name="SO_AGN")
    private String soAGN;

    @Column(name="SO_DANH_BAN")
    private String soDanhBan;

    @Column(name="SO_HO_CHIEU")
    private String soHoChieu;

    @Column(name="HO_TEN_CHA")
    private String hoTenCha;

    @Column(name="HO_TEN_ME")
    private String hoTenMe;

    @Column(name="DIA_CHI_THUONG_TRU")
    private String diaChiThuongTru;

    @Column(name="XA")
    private String xa;

    @Column(name="HUYEN")
    private String huyen;

    @Column(name="TINH")
    private String tinh;

    @Column(name="NGAY_LAP")
    private LocalDate ngayLap;

}