package com.mdm.supply.model.query;
import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true)
public class DrugQuery extends BasePageQuery {
    private String keyword;
    private Integer drugCategory;
    private Integer status;
    private String orgCode;
}
