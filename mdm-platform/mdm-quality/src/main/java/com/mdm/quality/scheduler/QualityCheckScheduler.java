package com.mdm.quality.scheduler;

import com.mdm.quality.report.QualityReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class QualityCheckScheduler {

    private final QualityReportService reportService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void dailyCheck() {
        log.info("开始执行每日质量检查...");
        try {
            reportService.getDailyReport(LocalDate.now().minusDays(1));
            log.info("每日质量检查完成");
        } catch (Exception e) {
            log.error("每日质量检查失败", e);
        }
    }
}
