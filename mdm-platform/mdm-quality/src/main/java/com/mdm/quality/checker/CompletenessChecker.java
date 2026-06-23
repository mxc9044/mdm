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
public class CompletenessChecker {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> check(String tableName, String[] requiredFields) {
        Map<String, Object> result = new HashMap<>();
        long total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName + " WHERE deleted = 0", Long.class);
        result.put("totalRecords", total);

        long incomplete = 0;
        for (String field : requiredFields) {
            long nullCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM " + tableName + " WHERE deleted = 0 AND (" + field + " IS NULL OR " + field + " = '')",
                    Long.class);
            if (nullCount > 0) {
                result.put("null_" + field, nullCount);
                incomplete += nullCount;
            }
        }
        double rate = total > 0 ? (double)(total - incomplete) / total * 100 : 100.0;
        result.put("completenessRate", Math.round(rate * 100.0) / 100.0);
        return result;
    }
}
