/*
 * package vn.leoo.document.job;
 * 
 * import java.text.SimpleDateFormat; import java.util.Date;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.scheduling.annotation.Scheduled; import
 * org.springframework.stereotype.Component;
 * 
 * import vn.leoo.common.dto.ResponseData; import
 * vn.leoo.document.service.ElasticseachService;
 * 
 * @Component public class ScheduledTask {
 * 
 * @Autowired private ElasticseachService hsEs;
 * 
 * @Value("${zone.data}") private Long vungDuLieu;
 * 
 * @Value("${scheduled.enable}") private boolean scheduledEnable;
 * 
 * @Scheduled(cron = "${scheduled.time.hoso}") public void jobMigrationHoSo()
 * throws Exception { if (scheduledEnable) {
 * 
 * SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
 * System.out.println(
 * "----------------------------------Task Migration Ho So--------------------------------HoSo: "
 * + fm.format(new Date()));
 * 
 * ResponseData res = hsEs.migrationHoSoData(10000);
 * 
 * } }
 * 
 * @Scheduled(cron = "${scheduled.time.a2}") public void jobMigrationTheA2()
 * throws Exception { if (scheduledEnable) { SimpleDateFormat fm = new
 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); System.out.
 * println("----------------------------------Task A2--------------------------------step 1: "
 * + fm.format(new Date()));
 * 
 * hsEs.migrationHoSoAndTheA2(10000);
 * 
 * } }
 * 
 * @Scheduled(cron = "${scheduled.time.a1}") public void jobMigrationTheA1()
 * throws Exception { if (scheduledEnable) { if (2 == vungDuLieu || 3 ==
 * vungDuLieu) { SimpleDateFormat fm = new
 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); System.out.
 * println("----------------------------------Task A1--------------------------------step 1: "
 * + fm.format(new Date())); hsEs.migrationHoSoAndTheA1(10000); } }
 * 
 * }
 * 
 * @Scheduled(cron = "${scheduled.time.a8}") public void jobMigrationTheA8()
 * throws Exception { if (scheduledEnable) { if (2 == vungDuLieu || 3 ==
 * vungDuLieu) { SimpleDateFormat fm = new
 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); System.out.
 * println("----------------------------------Task A8--------------------------------step 1: "
 * + fm.format(new Date())); hsEs.migrationHoSoAndTheA8(10000); } } }
 * 
 * }
 */