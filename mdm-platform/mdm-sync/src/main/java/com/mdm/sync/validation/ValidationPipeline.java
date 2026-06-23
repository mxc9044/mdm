package com.mdm.sync.validation;

import com.mdm.common.core.InboundDataDTO;
import com.mdm.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationPipeline {

    private final IdempotentValidator idempotentValidator;
    private final VersionValidator versionValidator;
    private final ApprovalValidator approvalValidator;
    private final StandardValidator standardValidator;
    private final RelationValidator relationValidator;

    public void validate(InboundDataDTO data, Integer currentVersion) {
        if (!idempotentValidator.validate(data)) {
            log.warn("重复请求被拦截: requestId={}", data.getRequestId());
            throw new ValidationException("重复请求: " + data.getRequestId());
        }

        if (!versionValidator.validate(data, currentVersion)) {
            log.warn("低版本数据被拒绝: dataCode={}, incoming={}, current={}",
                    data.getDataCode(), data.getVersion(), currentVersion);
            throw new ValidationException("数据版本冲突，当前版本: " + currentVersion);
        }

        if (!approvalValidator.validate(data)) {
            log.warn("未经审批的变更: dataCode={}", data.getDataCode());
        }

        List<String> errors = standardValidator.validate(data);
        if (!errors.isEmpty()) {
            throw new ValidationException("数据标准校验失败: " + String.join("; ", errors));
        }

        if (!relationValidator.validate(data.getDomainCode(), data.getPayload())) {
            throw new ValidationException("关联数据不完整");
        }
    }
}
