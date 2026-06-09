package vn.leoo.shopli.dto.dlsfilter;

import lombok.*;
import vn.leoo.common.util.DBTable;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenResultDTO {
    @DBTable(columnName = "id")
    private String id;
    @DBTable(columnName = "ho_ten")
    private String hoTen;
    @DBTable(columnName = "NAM_SINH")
    private Integer namSinh;
   /* @DBTable(columnName = "GIOI_TINH")
    private String gioiTinh;*/
    @DBTable(columnName = "SO_CCCD")
    private String soCccd;
    @DBTable(columnName = "SO_AGN")
    private String soAgn;
    @DBTable(columnName = "SO_CMND")
    private String soCmnd;
    @DBTable(columnName = "SO_DANH_BAN")
    private String soDanhBan;

    @DBTable(columnName = "SO_HO_CHIEU")
    private String soHoChieu;

    @DBTable(columnName = "HO_TEN_CHA")
    private String hoTenCha;

    @DBTable(columnName = "HO_TEN_ME")
    private String hoTenMe;

    @DBTable(columnName = "DIA_CHI_THUONG_TRU")
    private String diaChiThuongTru;

    @DBTable(columnName = "XA")
    private String xa;

    @DBTable(columnName = "HUYEN")
    private String huyen;

    @DBTable(columnName = "TINH")
    private String tinh;

    @DBTable(columnName = "NGAY_LAP")
    private Date ngayLap;
}













