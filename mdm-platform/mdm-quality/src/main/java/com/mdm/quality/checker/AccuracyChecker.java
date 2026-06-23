package com.mdm.quality.checker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccuracyChecker {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> check(String tableName, String codeField) {
        Map<String, Object> result = new HashMap<>();
        long total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName + " WHERE deleted = 0", Long.class);
        result.put("totalRecords", total);
        long invalidCode = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName + " WHERE deleted = 0 AND (LENGTH(" + codeField + ") < 3)",
                Long.class);
        result.put("invalidCodeCount", invalidCode);
        double rate = total > 0 ? (double)(total - invalidCode) / total * 100 : 100.0;
        result.put("accuracyRate", Math.round(rate * 100.0) / 100.0);
        return result;
    }
}
