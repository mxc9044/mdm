package com.mdm.supply.model.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true) @TableName("mdm_supply_item")
public class SupplyItem extends BaseEntity {
    private String supplyItemCode;
    private String supplyItemName;
    private Integer itemCategory;
    private String specification;
    private String unit;
    private String orgCode;
    private String brand;
    private Integer storageCondition;
    private Integer status;
    @Version private Integer version;
    private String remark;
}
