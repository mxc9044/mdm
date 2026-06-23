package com.mdm.organization.model.query;

import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrgQuery extends BasePageQuery {
    private String keyword;
    private Integer orgType;
    private Integer status;
    private String parentCode;
}
