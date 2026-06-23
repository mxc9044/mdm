package com.mdm.sync.validation;

import com.mdm.common.core.InboundDataDTO;
import com.mdm.common.core.MdmConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class IdempotentValidator {
    private final StringRedisTemplate redisTemplate;

    public boolean validate(InboundDataDTO data) {
        String key = MdmConstants.IDEMPOTENT_PREFIX + data.getRequestId();
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, "1", 48, TimeUnit.HOURS);
        return Boolean.TRUE.equals(absent);
    }
}
