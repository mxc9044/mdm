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
public class ConsistencyChecker {

    private final JdbcTemplate jdbcTemplate;

    public Map<String, Object> check() {
        Map<String, Object> result = new HashMap<>();
        long orphanDepts = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM mdm_dept d WHERE d.deleted = 0 AND d.org_code NOT IN (SELECT org_code FROM mdm_org WHERE deleted = 0)",
                Long.class);
        result.put("orphanDepts", orphanDepts);

        long orphanEmps = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM mdm_employee e WHERE e.deleted = 0 AND e.dept_code NOT IN (SELECT dept_code FROM mdm_dept WHERE deleted = 0)",
                Long.class);
        result.put("orphanEmployees", orphanEmps);

        result.put("consistent", orphanDepts == 0 && orphanEmps == 0);
        return result;
    }
}
