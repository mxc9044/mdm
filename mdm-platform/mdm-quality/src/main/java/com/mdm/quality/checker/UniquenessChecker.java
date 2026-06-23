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
public class UniquenessChecker {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> check(String tableName, String codeField) {
        Map<String, Object> result = new HashMap<>();
        long total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName + " WHERE deleted = 0", Long.class);
        long distinct = jdbcTemplate.queryForObject(
                "SELECT COUNT(DISTINCT " + codeField + ") FROM " + tableName + " WHERE deleted = 0", Long.class);
        result.put("totalRecords", total);
        result.put("distinctRecords", distinct);
        result.put("duplicateCount", total - distinct);
        result.put("uniquenessRate", total > 0 ? Math.round((double) distinct / total * 10000.0) / 100.0 : 100.0);
        return result;
    }
}
