package com.mdm.supply.model.query;
import com.mdm.common.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true)
public class MaterialQuery extends BasePageQuery {
    private String keyword;
    private Integer materialCategory;
    private Integer status;
}
