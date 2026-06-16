package vn.leoo.audit.log.config;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
@Getter
@lombok.Builder
public  class DiffOptions {

    /**
     * Coi {@code null}, {@code ""}, {@code "   "} là như nhau.
     * <p>Ví dụ: {@code null} vs {@code ""} → không ghi log thay đổi</p>
     * Default: {@code true}
     */
    @lombok.Builder.Default
    boolean treatNullAsEmpty = true;

    /**
     * Bỏ qua field có trong {@code newObj} nhưng không có trong {@code oldObj}.
     * <p>Dùng khi entity có field mới thêm, không muốn log toàn bộ là "thay đổi"</p>
     * Default: {@code false}
     */
    @lombok.Builder.Default
    boolean ignoreNewField = false;

    /**
     * Danh sách field bị bỏ qua khi so sánh diff.
     * <p>Mặc định bỏ qua các field hệ thống: id, createdAt, createdBy, updatedAt, updatedBy</p>
     */
    @lombok.Builder.Default
    Set<String> ignoreFields = Set.of(
            "id", "createdAt", "createdBy", "updatedAt", "updatedBy"
    );

    /**
     * So sánh giá trị dưới dạng String để tránh lệch kiểu dữ liệu.
     * <p>Ví dụ: {@code 1} (Integer) vs {@code "1"} (String) → coi là bằng nhau</p>
     * <p>Chỉ áp dụng khi cả 2 giá trị đều không null/empty</p>
     * Default: {@code false}
     */
    @lombok.Builder.Default
    boolean compareAsString = false;

    /**
     * Tạo DiffOptions với cấu hình mặc định.
     * <ul>
     *   <li>treatNullAsEmpty = true</li>
     *   <li>ignoreNewField   = false</li>
     *   <li>compareAsString  = false</li>
     *   <li>ignoreFields     = [id, createdAt, createdBy, updatedAt, updatedBy]</li>
     * </ul>
     */
    public static DiffOptions defaults() {
        return DiffOptions.builder().build();
    }

    /**
     * Tạo DiffOptions với chế độ nghiêm ngặt.
     * <ul>
     *   <li>treatNullAsEmpty = false → null ≠ ""</li>
     *   <li>compareAsString  = false → 1 ≠ "1"</li>
     * </ul>
     */
    public static DiffOptions strict() {
        return DiffOptions.builder()
                .treatNullAsEmpty(false)
                .compareAsString(false)
                .build();
    }
}