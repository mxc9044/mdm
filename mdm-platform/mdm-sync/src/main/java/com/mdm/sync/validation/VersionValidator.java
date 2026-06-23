package com.mdm.sync.validation;

import com.mdm.common.core.InboundDataDTO;
import org.springframework.stereotype.Component;

@Component
public class VersionValidator {
    public boolean validate(InboundDataDTO data, Integer currentVersion) {
        if (data.getVersion() == null || currentVersion == null) return true;
        return data.getVersion() > currentVersion;
    }
}
