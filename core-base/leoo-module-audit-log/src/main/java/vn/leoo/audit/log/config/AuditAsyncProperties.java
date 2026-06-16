package vn.leoo.audit.log.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cấu hình Thread Pool cho audit log, đọc từ {@code application.yml}.
 *
 * <pre>{@code
 * audit:
 *   async:
 *     core-pool-size: 2       # số thread luôn sống
 *     max-pool-size: 5        # số thread tối đa khi bận
 *     queue-capacity: 500     # số task xếp hàng chờ
 *     keep-alive-seconds: 60  # thời gian sống của thread nhàn rỗi
 * }</pre>
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "audit.async")
public class AuditAsyncProperties {

    /**
     * Số thread luôn duy trì sống dù không có task.
     * Default: {@code 2}
     */
    private int corePoolSize = 2;

    /**
     * Số thread tối đa được tạo khi queue đầy.
     * Default: {@code 5}
     */
    private int maxPoolSize = 5;

    /**
     * Số lượng task tối đa xếp hàng chờ trước khi tạo thêm thread.
     * <p>Khi queue đầy và đã đạt {@code maxPoolSize} →
     * {@code CallerRunsPolicy} chạy task trên thread gọi.</p>
     * Default: {@code 500}
     */
    private int queueCapacity = 500;

    /**
     * Thời gian (giây) thread nhàn rỗi vượt quá {@code corePoolSize} tồn tại
     * trước khi bị terminate.
     * Default: {@code 60}
     */
    private int keepAliveSeconds = 60;

    /**
     * Thời gian tối đa (giây) chờ các task hoàn thành khi shutdown.
     *
     * Sau 60 giây nếu vẫn còn task chưa xong → buộc tắt (interrupt).
     *
     * Cần cân nhắc:
     * - Đặt quá thấp → task ghi log bị cắt ngang
     * - Đặt quá cao → deploy/restart bị treo lâu
     * - Nên >= thời gian ghi log lâu nhất có thể xảy ra
     */
    private  boolean  waitForTasksToCompleteOnShutdown = true;
    /**
     * Thời gian tối đa (giây) chờ các task audit hoàn thành khi ứng dụng shutdown.
     *
     * <p>Phối hợp với {@code waitForTasksToCompleteOnShutdown = true}:</p>
     * <ul>
     *   <li>Ứng dụng nhận SIGTERM → Spring bắt đầu shutdown</li>
     *   <li>Chờ tối đa {@code awaitTerminationSeconds} giây</li>
     *   <li>Hết thời gian → buộc tắt dù task chưa hoàn thành</li>
     * </ul>
     *
     * <p>Khuyến nghị: đặt bằng hoặc lớn hơn timeout của một lần ghi DB.</p>
     * Default: {@code 60}
     */
    private int waitTerminationSeconds = 60;
}