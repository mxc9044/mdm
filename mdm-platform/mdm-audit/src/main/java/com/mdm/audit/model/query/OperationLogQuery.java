package com.mdm.audit.model.query;

import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogQuery extends BasePageQuery {
    private String module;
    private String operator;
    private String startTime;
    private String endTime;
}
