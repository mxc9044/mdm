package com.mdm.organization.model.query;

import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeQuery extends BasePageQuery {
    private String keyword;
    private String orgCode;
    private String deptCode;
    private Integer practiceType;
    private Integer status;
}
