package com.mdm.audit.model.query;

import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeHistoryQuery extends BasePageQuery {
    private String dataDomain;
    private String dataCode;
    private String changeType;
    private String operator;
    private String startTime;
    private String endTime;
}
