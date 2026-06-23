package com.mdm.clinical.model.query;

import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TreatmentItemQuery extends BasePageQuery {

    private String keyword;

    private Integer itemCategory;

    private Integer status;
}
