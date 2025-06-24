package vn.leoo.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Application constants.
 */
public final class Constants {

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

	public static final String SYSTEM = "system";
	public static final String DEFAULT_LANGUAGE = "vi";

	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static final String ASCENDING = "asc";

	public final static String TOKEN = "Authorization";
	public final static String SEPARATE = "###";
	public final static String SUB_SEPARATE = ":";

	public final static String ACCOUNT_PASSWORD_DEFAULT = "account.password.default";

	public final static String HTTP_REQUEST_POST_METHOD = "POST";
	public final static String HTTP_REQUEST_GET_METHOD = "GET";

	public final static int HTTP_CONTINUE = 100;
	public final static int HTTP_SWITCHING_PROTOCOLS = 101;
	public final static int HTTP_PROCESSING = 102; // RFC2518
	public final static int HTTP_OK = 200;
	public final static int HTTP_CREATED = 201;
	public final static int HTTP_ACCEPTED = 202;
	public final static int HTTP_NON_AUTHORITATIVE_INFORMATION = 203;
	public final static int HTTP_NO_CONTENT = 204;
	public final static int HTTP_RESET_CONTENT = 205;
	public final static int HTTP_PARTIAL_CONTENT = 206;
	public final static int HTTP_MULTI_STATUS = 207; // RFC4918
	public final static int HTTP_ALREADY_REPORTED = 208; // RFC5842
	public final static int HTTP_IM_USED = 226; // RFC3229
	public final static int HTTP_MULTIPLE_CHOICES = 300;
	public final static int HTTP_MOVED_PERMANENTLY = 301;
	public final static int HTTP_FOUND = 302;
	public final static int HTTP_SEE_OTHER = 303;
	public final static int HTTP_NOT_MODIFIED = 304;
	public final static int HTTP_USE_PROXY = 305;
	public final static int HTTP_RESERVED = 306;
	public final static int HTTP_TEMPORARY_REDIRECT = 307;
	public final static int HTTP_PERMANENTLY_REDIRECT = 308; // RFC7238
	public final static int HTTP_BAD_REQUEST = 400;
	public final static int HTTP_UNAUTHORIZED = 401;
	public final static int HTTP_PAYMENT_REQUIRED = 402;
	public final static int HTTP_FORBIDDEN = 403;
	public final static int HTTP_NOT_FOUND = 404;
	public final static int HTTP_METHOD_NOT_ALLOWED = 405;
	public final static int HTTP_NOT_ACCEPTABLE = 406;
	public final static int HTTP_PROXY_AUTHENTICATION_REQUIRED = 407;
	public final static int HTTP_REQUEST_TIMEOUT = 408;
	public final static int HTTP_CONFLICT = 409;
	public final static int HTTP_GONE = 410;
	public final static int HTTP_LENGTH_REQUIRED = 411;
	public final static int HTTP_PRECONDITION_FAILED = 412;
	public final static int HTTP_REQUEST_ENTITY_TOO_LARGE = 413;
	public final static int HTTP_REQUEST_URI_TOO_LONG = 414;
	public final static int HTTP_UNSUPPORTED_MEDIA_TYPE = 415;
	public final static int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
	public final static int HTTP_EXPECTATION_FAILED = 417;
	public final static int HTTP_I_AM_A_TEAPOT = 418; // RFC2324
	public final static int HTTP_MISDIRECTED_REQUEST = 421; // RFC7540
	public final static int HTTP_UNPROCESSABLE_ENTITY = 422; // RFC4918
	public final static int HTTP_LOCKED = 423; // RFC4918
	public final static int HTTP_FAILED_DEPENDENCY = 424; // RFC4918
	public final static int HTTP_RESERVED_FOR_WEBDAV_ADVANCED_COLLECTIONS_EXPIRED_PROPOSAL = 425; // RFC2817
	public final static int HTTP_UPGRADE_REQUIRED = 426; // RFC2817
	public final static int HTTP_PRECONDITION_REQUIRED = 428; // RFC6585
	public final static int HTTP_TOO_MANY_REQUESTS = 429; // RFC6585
	public final static int HTTP_REQUEST_HEADER_FIELDS_TOO_LARGE = 431; // RFC6585
	public final static int HTTP_UNAVAILABLE_FOR_LEGAL_REASONS = 451;
	public final static int HTTP_INTERNAL_SERVER_ERROR = 500;
	public final static int HTTP_NOT_IMPLEMENTED = 501;
	public final static int HTTP_BAD_GATEWAY = 502;
	public final static int HTTP_SERVICE_UNAVAILABLE = 503;
	public final static int HTTP_GATEWAY_TIMEOUT = 504;
	public final static int HTTP_VERSION_NOT_SUPPORTED = 505;
	public final static int HTTP_VARIANT_ALSO_NEGOTIATES_EXPERIMENTAL = 506; // RFC2295
	public final static int HTTP_INSUFFICIENT_STORAGE = 507; // RFC4918
	public final static int HTTP_LOOP_DETECTED = 508; // RFC5842
	public final static int HTTP_NOT_EXTENDED = 510; // RFC2774
	public final static int HTTP_NETWORK_AUTHENTICATION_REQUIRED = 511;

	public static final String SPRING_ENVIRONMENT = "spring.environment";

	/*** Folder path ***/
	public final static String ROOT_DIR = "ROOT_DIR";
	public final static String APP_ROOT_LOCATION = "APP_ROOT_LOCATION";
	public final static String APP_CONFIG_LOCATION = "APP_CONFIG_LOCATION";
	public final static String SPRING_CONFIG_LOCATION = "SPRING_CONFIG_LOCATION";
	public final static String SPRING_CONFIG_NAME = "SPRING_CONFIG_NAME";
	public final static String SPRING_PROFILE_DEVELOPMENT = "dev";

	public final static String APP_ENPOINT = "_ENPOINT";

	public static final String BLOCK_SIZE = "app.file.block_size";

	/** The Constant BLANK. */
	public static final String BLANK = "";

	/** The Constant COLON. */
	public static final String COLON = " : ";

	/** The Constant DASH. */
	public static final String DASH = " - ";

	/** The Constant KEY_TXNID. */
	public static final String KEY_TXNID = "TXNID";

	/** The Constant XPATH_TXNID. */
	public static final String XPATH_TXNID = "//transcationId";

	/** The Constant ENTRY. */
	public static final String ENTRY = "Entry";

	/** The Constant EXIT. */
	public static final String EXIT = "Exit";


	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class JwtClaimName {
		public static final String USERNAME = "username";
		public static final String USER_INFO = "user_info";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class LOGIN_RESULT {
		public static final String SUCCESS = "SUCCESS";
		public static final String FAIL = "FAIL";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class REDIS_KEY {
		public static final String ROLE = "ROLE_";
		public static final String ROLE_APP = "ROLE_APP_";
		public static final String APP_BY_CODE = "APP_BY_CODE_";
		public static final String DEFAULT_PW = "DEFAULT_PW";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ACCOUNT_STATUS {
		public static final Long ACTIVE = 1L;
		public static final Long INACTIVE = 2L;
		public static final Long LOCKED = 3L;
		public static final Long DELETED = -1L;
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class TYPE_LOG {
		public static final String LOGIN = "log_type_login";
		public static final String STATRT_LOG = "START_LOG_JSON ";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ROW_STATUS {
		public static final Long ACTIVE = 0L;
		public static final Long DELETED = 1L;
	}


	

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class ORACLE_PO {
		public static final String REF_CURSOR = "po_result";
		public static final String ERROR_MSG = "po_error_msg";
		public static final String TOTAL = "po_total";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class REGEX_PATTERN {

		// không chưa ký tự đặc biệt
		// public static final String ADDRESS_NOT_CONTAIN =
		// "^((?!@|`|~|!|#|$|%|\\*|\\^).)*$";
		public static final String STRING_NOT_CONTAIN = "^((?!@|`|~|!|#|$|%|\\*|\\^).)*$";

		public static final String SO_DANG_KY = "[A-Z]{2}[-| ][1-9]{1,6}[-|/][1-2]{1}[0-9]{3}$";

	}


	
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class FORMAT_RESULT_TRACUU {
		// Du lieu moi
		public static final String MESSSAGE_CODE = "200";
		// Du lieu hoi co
		public static final String KEY_CODE = "messageCode";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class MESSAGE {
		/**
		 * Có lỗi xảy ra khi tra cứu
		 */
		public static final String TRACUU_FAILED = "Có lỗi khi gửi tra cứu";
		/**
		 * Có lỗi xảy ra
		 */
		public static final String NOT_FOUND_HO_SO_BY_BIEU_MAU = "Biểu mẫu không thuộc hồ sơ nào";
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
		 * Thêm mới thành công
		 */
		public static final String EXIS_DIEN_BIEN = "Hồ sơ còn tồn tại diễn biến chưa được ký duyệt ";
		public static final String EXIS_DIEN_BIEN_KET_THUC = "Hồ sơ đã tồn tại diễn biến kết thúc  ";
		public static final String EXIS_DIEN_BIEN_MO_TAP = "Hồ sơ đã tồn tại diễn biến mở tập chưa hoàn thành  ";
		/**
		 * Có lỗi xảy ra
		 */
		public static final String EXPECTATION_FAILED = "[#417] Yêu cầu xử lý không thành công, vui lòng liên hệ quản trị viên";
		/**
		 * Không có nội dung
		 */
		public static final String NO_CONTENT = "Không có nội dung";
		/**
		 * Không tìm thấy
		 */
		public static final String NOT_FOUND = "Không tìm thấy";
		/**
		 * Không tìm thấy
		 */
		public static final String HO_SO_NOT_FOUND = "Không tìm thấy thông tin hồ sơ ";
		/**
		 * Không tìm thấy
		 */
		public static final String NOT_FOUND_DOI_TUONG = "Không tìm thấy danh sách đối tượng";
		/**
		 * Dữ liệu không tồn tại
		 */
		public static final String DATA_NOT_EXIST = "Dữ liệu không tồn tại";

		/**
		 * Dữ liệu không tồn tại
		 */
		public static final String MA_YEU_CAU_DATA_NOT_EXIST = "Mã yêu cầu không tồn tại";

		/**
		 * Dữ liệu đã tồn tại
		 */
		public static final String DATA_EXIST = "Dữ liệu đã tồn tại";
		/**
		 * Trùng số đăng ký
		 */
		public static final String DUPLICATE_REGISTER_NUMBER = "Số đăng ký đã tồn tại";
		/**
		 * Máy chủ quá thời gian xử lý
		 */
		public static final String REQUEST_TIMEOUT = "[#408] Máy chủ quá thời gian xử lý";
		/**
		 * Xung đột xảy ra
		 */

		public static final String CONFLICT = "[#409] Dữ liệu hoặc nghiệp vụ đã tồn tại";
		public static final String SERVICE_UNAVAILABLE = "[#503] Không thành công, vui lòng liên hệ quản trị viên";
		/**
		 * Độ dài nội dung
		 */
		public static final String CONTENT_LENGTH = "Độ dài nội dung";
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
		 * Không tìm thấy bản tin.
		 */
		public static final String NOT_ACCEPTABLE = "[#406] Dữ liệu không hợp lệ";

		/**
		 * Hành động không hợp lệ.
		 */
		public static final String HANDLE_NOT_ACCEPTABLE = "Hành động không hợp lệ";

		/**
		 * Phải nhỏ hơn ngày hiện tại
		 */
		public static final String DATE_LESS_THAN_DATE_TODAY = "Phải nhỏ hơn ngày hiện tại";

		/**
		 * Phải nhỏ hơn hoặc bằng ngày hiện tại
		 */
		public static final String DATE_LESS_THAN_OR_EQUAL_DATE_TODAY = "Phải nhỏ hơn hoặc bằng ngày hiện tại";

		/**
		 * Phải nhỏ hơn ngày hiện tại
		 */
		public static final String DATE_GREATER_THAN_DATE_TODAY = "Phải lớn hơn ngày hiện tại";

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
		/**
		 * s% .s%s%
		 */
		public static final String RESULT_STR_FORMAT = "%s .%s%s";

		public static final String SO_HO_CHIEU_FORMAT = " Số Hộ chiếu phải bắt đầu bằng 1 chữ cái in hoa và 7 chữ số";

		public static final String CONSTRAINT_NATIONAL_CCCD = " Quốc tịch Việt Nam phải có CMT/CCCD";

		public static final String CONSTRAINT_NATIONAL_HC = " Quốc tịch nước ngoài phải có Hộ chiếu";

		public static final String CONSTRAINT_NATIONAL_REASON = " Lý do không thể nhập nếu có CCCD hoặc Hộ chiếu";

		public static final String NGAY_SINH = " Ngày sinh không được lớn hơn ngày hiện tại";

		public static final String INVALID_STATUS = "Trạng thái cập nhật không hợp lệ";
		public static final String ERROR_SAME_ID_HO_SO_SNCL = "id hồ sơ diến biến không được giống id hồ sơ đăng ký sát nhập/chuyến loại";
		public static final String ERROR_SAME_LOAI_HO_SO = "Loại hồ sơ của hồ sơ chủ trì và hồ sơ phối hợp không được khác nhau";

		/**
		 * Không được chứa các ký tự đặc biệt
		 */
		public static final String NOT_ALLOW_CHARACTER_MESSAGE_KEY_SEARCH = "Từ khóa tìm kiếm không được chứa các ký tự : `, ~, !, @, #, $, %, ^, *";
		public static final String NOT_ALLOW_CHARACTER_MESSAGE_MA_YEU_CAU = "Mã yêu cầu không được chứa các ký tự : `, ~, !, @, #, $, %, ^, *";
		public static final String NOT_ALLOW_CHARACTER_MESSAGE_TRICH_YEU = "Trích yếu không được chứa các ký tự : `, ~, !, @, #, $, %, ^, *";
		public static final String NOT_ALLOW_CHARACTER_MESSAGE_NOI_DUNG_PHOI_HOP = "Nội dung phối hợp không được chứa các ký tự : `, ~, !, @, #, $, %, ^, *";

		/**
		 * Độ dài chuỗi không đúng
		 */
		public static final String NOT_ALLOW_KEY_SEARCH_LENGTH = "Từ khóa tìm kiếm không được quá 100 ký tự";
		public static final String NOT_ALLOW_MA_YEU_CAU_LENGTH = "Mã yêu cầu không được quá 40 ký tự";
		public static final String NOT_ALLOW_TRICH_YEU_LENGTH = "Trích yếu không được quá 4000 ký tự";
		public static final String NOT_ALLOW_NOI_DUNG_PHOI_HOP_LENGTH = "Trích yếu không được quá 1000 ký tự";
		public static final String SEND_NOTIFICATION_SUCCESS = "Gửi thông báo Thành công, mã yêu cầu : ";
		public static final String SEND_NOTIFICATION_FAIL = "Gửi thông báo Thất bại, mã yêu cầu : ";

		/**
		 * Sát nhập/Chuyển loại
		 */
		public static final String MA_LOAI_HS_CHUYEN_LOAI = "Loại hồ sơ của hồ sơ chính không được cùng loại với hồ sơ diễn biến";
		public static final String MA_LOAI_HS_SAT_NHAP = "Loại hồ sơ của hồ sơ chính phải cùng loại với hồ sơ diễn biến";
		public static final String DONVI_SATNHAP_CHUYENLOAI = "Hồ sơ chính phải cùng đơn vị với hồ sơ diễn biến";

		public static final String CHECK_ACCOUNT = "Không tìm thấy thông tin cán bộ";

		public static final String CHECK_HO_SO_LIEN_QUAN_TRUE = "Số hồ sơ liên quan hợp lệ";
		public static final String CHECK_HO_SO_LIEN_QUAN_FALSE = "Số hồ sơ liên quan không hợp lệ";
		/**
		 * Không tìm thấy thông tin thẻ
		 */
		public static final String NOT_FOUND_A1 = "Không tìm thấy thông tin thẻ lực lượng của hồ sơ";

		/**
		 * loai ho so sai
		 */
		public static final String LOI_LOAI_HO_SO = "Loại hồ sơ không được phép phối hợp ";

		/**
		 * Không tìm thấy
		 */
		public static final String LOAI_HO_SO_SAT_NHAP = "Loại hồ sơ chính không hợp lệ";
		public static final String LOAI_HO_SO_DTHS = "Loại hồ sơ không hợp lệ";

		public static final String ERROR_SAME_SO_DANG_KY = "Số đăng ký đã tồn tại ";
		/**
		 * Không thể gọi đến API: %s, error: %s
		 */
		public static final String API_NO_CONTENT_RESULT_FORMAT_TRA_CUU = "Không thể gọi đến API tra cứu B5, error: %s";
		public static final String FORMAT_NOT_VALID = "Không đúng định dạng";
		public static final String BIEU_MAU_B5_NOT_EXIST = "Không tồn tại biểu mẫu tra cứu";

		public static final String HO_SO_CHUA_KET_THUC = "Hồ sơ chưa kết thúc";
		public static final String INVALID_MA_LOAI_DOI_TUONG = "Mã loại đối tượng không hợp lệ";
		public static final String EXIST_DIEN_BIEN_CHUA_KY_DUYET = "Hồ sơ còn tồn tại diễn biến chưa ký duyệt";
		public static final String HO_SO_KET_THUC = "Hồ sơ đã kết thúc";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class CONTENT_NOTIFILE {
		public static final String KET_QUA_TRA_CUU = "Đã có kết quả tra cứu B5 với đối tượng ";
	}
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class VALID {
		public static final int MIN_PAGE = 1;
		public static final int MAX_PAGE = 99999999;
		public static final int MAX_SIZE = 99999999;
		public static final int MIN_SIZE = 10;
		public static final int MAX_DESC = 200;
		public static final int MIN_KEY_SEARCH = 0;
		public static final int MAX_KEY_SEARCH = 100;
		public static final int MIN_MA_YEU_CAU = 0;
		public static final int MAX_MA_YEU_CAU = 40;
		public static final int MIN_TRICH_YEU = 0;
		public static final int MAX_TRICH_YEU = 4000;
		public static final int MAX_SO_DANG_KY = 10;
	}

	

}
