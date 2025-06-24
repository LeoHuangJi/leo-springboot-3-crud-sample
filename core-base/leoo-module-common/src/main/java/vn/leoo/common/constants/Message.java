package vn.leoo.common.constants;

public  final class Message {
    // public static final String METHOD_NOT_ALLOWED = "Yêu cầu thực hiện thành
    // công";

    /**
     * Yêu cầu thực hiện thành công
     */
    public static final String SUCCESS = "Yêu cầu thực hiện thành công";
    /**
     * Yêu cầu thực hiện thành công
     */
    public static final String OK = "Yêu cầu thực hiện thành công";
    /**
     * Yêu cầu xử lý thất bại
     */
    public static final String ERROR = "[#500] Yêu cầu xử lý không thành công, vui lòng liên hệ quản trị viên";
    /**
     * Lỗi máy chủ xử lý
     */
    public static final String SERVER_ERROR = "[#500] Yêu cầu xử lý không thành công, vui lòng liên hệ quản trị viên";
    /**
     * Có lỗi trong yêu cầu xử lý
     */
    public static final String BAD_REQUEST = "[#400] Yêu cầu xử lý không thành công, vui lòng liên hệ quản trị viên";
    /**
     * Thêm mới thành công
     */
    public static final String CREATED = "Thêm mới thành công";
    /**
     * Có lỗi xảy ra
     */
    public static final String EXPECTATION_FAILED = "[#417] Không thành công, vui lòng liên hệ quản trị viên";
    /**
     * Không có nội dung
     */
    public static final String NO_CONTENT = "Không có dữ liệu hoặc bản tin";
    /**
     * Không tìm thấy
     */
    public static final String NOT_FOUND = "Không tìm thấy dữ liệu";
    /**
     * Máy chủ quá thời gian xử lý
     */
    public static final String REQUEST_TIMEOUT = "[#408] Máy chủ quá thời gian xử lý";
    /**
     * Xung đột xảy ra
     */
    public static final String CONFLICT = "Dữ liệu hoặc nghiệp vụ đã tồn tại";
    /**
     * Xung đột xảy ra
     */
    public static final String CONTENT_LENGTH = "Xung đột xảy ra";
    /**
     * Yêu cầu không được máy chủ thực hiện
     */
    public static final String NOT_IMPLEMENTED = "Yêu cầu không được máy chủ thực hiện";
    /**
     * Dịch vụ yêu cầu thực hiện không có sẵn
     */
    public static final String SERVICE_UNAVILABLE = "Dịch vụ yêu cầu thực hiện không có sẵn";
    /**
     * Không được để trống
     */
    public static final String FIELD_NOT_NULL = "Không được để trống";
    /**
     * Không được để trống
     */
    public static final String FIELD_NOT_CONTAIN_SPECIAL_CHARACTER = "Không được chưa ký tự đặc biệt: `, ~, !, @, #, $, %, ^,*";
    /**
     * Dữ liệu không hợp lệ
     */
    public static final String NOT_ACCEPTABLE = "Dữ liệu không hợp lệ";
    /**
     * Chưa chọn giá trị
     */
    public static final String SELECT_NOT_NULL = "Chưa chọn giá trị";

    /**
     * Có lỗi trong quá trình gọi đến API: http:*******
     */
    public static final String API_BAD_REQUEST = "[#400] Có lỗi trong quá trình gọi đến API: http:*******";
    /**
     * Không có dữ liệu trả về từ API: http:*******
     */
    public static final String API_NO_CONTENT_RESULT = "Không có dữ liệu trả về từ API: http:*******";

    /**
     * Có lỗi trong quá trình gọi đến API: %s, status: %s, error: %s
     */
    public static final String API_BAD_REQUEST_FORMAT = "[#400] Lỗi trong quá trình gọi đến API: %s, status: %s, error: %s";
    /**
     * Không thể gọi đến API: %s, error: %s
     */
    public static final String API_NO_CONTENT_RESULT_FORMAT = "Không thể gọi đến API: %s, error: %s";

    public static final String RESULT_STR_FORMAT = "%s .%s%s";
    public static final String INTERNAL_SERVER_ERROR = "Đã có lỗi xảy ra, xin vui lòng liên hệ quản trị viên";
    
    /* Hồ sơ A3 */
    public static final String FIELD_MAX_LENGTH = "Độ dài không vượt quá %s ký tự";
    public static final String FIELD_MIN_LENGTH = "Độ dài tối thiểu %s ký tự";
    public static final String BAD_REQUEST_PEOPLE_DUPLICATE = "Thông tin đối tượng có dấu hiệu trùng lặp";
    public static final String FORBIDDEN = "Không có quyền thao tác";
    public static final String FORMAT_NOT_VALID = "Không đúng định dạng";
    public static final String ERROR_SAME_SO_DANG_KY = "Số đăng ký đã tồn tại ";
    public static final String ERROR_SAME_LOAI_HO_SO = "Loại hồ sơ của hồ sơ chủ trì và hồ sơ phối hợp không được giống nhau";
    public static final String ERROR_SAME_SO_DANG_KY_HS_CHU_TRI = "Trùng số đăng ký với hồ sơ chủ trì ";
    public static final String EXIS_DIEN_BIEN = "Hồ sơ còn tồn tại diễn biến chưa được ký duyệt ";
    public static final String ERROR_TRANG_THAI_TU_CHOI = "Yêu cầu từ chối không thể gửi lại yêu cầu";
    /**
     * Không có nội dung
     */
    public static final String NO_CONTENT_HO_SO = "Hồ sơ không tồn tại trong hệ thống";
}
