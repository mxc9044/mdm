package com.mdm.quality.controller;

import com.mdm.common.core.Result;
import com.mdm.quality.report.QualityReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quality")
@RequiredArgsConstructor
public class QualityController {

    private final QualityReportService reportService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        return Result.ok(reportService.getDashboard());
    }

    @GetMapping("/reports/daily")
    public Result<Map<String, Object>> dailyReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) date = LocalDate.now().minusDays(1);
        return Result.ok(reportService.getDailyReport(date));
    }

    @GetMapping("/reports/weekly")
    public Result<Map<String, Object>> weeklyReport() {
        return Result.ok(reportService.getWeeklyReport());
    }
}
