package vn.leoo.shopli.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.leoo.shopli.service.EsToOracleSyncService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/sync")
public class SyncController {

    private final EsToOracleSyncService syncService;

    @PostMapping("/audit-log")
    public ResponseEntity<String> startSync() {
        // Nên chạy async, không block request
        CompletableFuture.runAsync(() -> syncService.syncAll("audit_log_index"));
        return ResponseEntity.accepted().body("Sync job started, check logs for progress");
    }
    @PostMapping("/audit-log/reset")
    public ResponseEntity<String> reset() {
        syncService.resetCheckpoint();
        return ResponseEntity.ok("Checkpoint đã reset, job sẽ chạy lại từ đầu khi gọi /audit-log");
    }
}