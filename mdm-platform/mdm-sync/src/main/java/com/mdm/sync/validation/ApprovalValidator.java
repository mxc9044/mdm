package com.mdm.sync.validation;

import com.mdm.common.core.InboundDataDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ApprovalValidator {
    public boolean validate(InboundDataDTO data) {
        return StringUtils.hasText(data.getApprovalNo());
    }
}
