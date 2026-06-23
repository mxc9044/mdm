package com.mdm.sync.validation;

import com.mdm.common.core.InboundDataDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StandardValidator {
    public List<String> validate(InboundDataDTO data) {
        List<String> errors = new ArrayList<>();
        Map<String, Object> payload = data.getPayload();
        if (payload == null || payload.isEmpty()) {
            errors.add("数据内容不能为空");
        }
        return errors;
    }
}
