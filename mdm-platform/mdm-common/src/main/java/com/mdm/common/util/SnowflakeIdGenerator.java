package com.mdm.common.util;

import com.mdm.common.core.MdmConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {

    private final StringRedisTemplate redisTemplate;

    public SnowflakeIdGenerator(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateCode(String prefix, int digits) {
        String key = MdmConstants.SEQ_PREFIX + prefix;
        Long seq = redisTemplate.opsForValue().increment(key);
        if (seq == null) {
            throw new RuntimeException("Redis 序列生成失败: " + key);
        }
        return prefix + String.format("%0" + digits + "d", seq);
    }

    // ORG、STD 六位，其他默认八位
    public String generateCode(String prefix) {
        if ("ORG".equals(prefix) || "STD".equals(prefix)) {
            return generateCode(prefix, 6);
        }
        return generateCode(prefix, 8);
    }
}
