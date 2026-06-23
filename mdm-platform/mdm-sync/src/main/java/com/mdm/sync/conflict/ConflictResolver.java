package com.mdm.sync.conflict;

import com.mdm.common.core.MdmConstants;
import org.springframework.stereotype.Component;

@Component
public class ConflictResolver {
    // 停用/注销直接盖，其余比版本号
    public boolean shouldOverride(String operationType, Integer incomingVersion, Integer currentVersion) {
        if (MdmConstants.CHANGE_DISABLE.equals(operationType) || MdmConstants.CHANGE_CANCEL.equals(operationType)) {
            return true;
        }
        if (incomingVersion == null || currentVersion == null) return true;
        return incomingVersion > currentVersion;
    }
}
