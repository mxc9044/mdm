package com.mdm.facility.model.query;

import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceQuery extends BasePageQuery {
    private String keyword;
    private Integer deviceCategory;
    private String orgCode;
    private String deptCode;
    private Integer status;
}
