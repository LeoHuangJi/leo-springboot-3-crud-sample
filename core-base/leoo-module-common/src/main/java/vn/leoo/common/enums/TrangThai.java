package vn.leoo.common.enums;

public enum TrangThai {
	DRAFT("001", "Nháp"), PUBLISHED("002", "Đã xuất bản"), ARCHIVED("003", "Lưu trữ");

	private final String code;
	private final String label;

	TrangThai(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static TrangThai fromCode(String code) {
		for (TrangThai status : values()) {
			if (status.code.equalsIgnoreCase(code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid code: " + code);
	}
}
