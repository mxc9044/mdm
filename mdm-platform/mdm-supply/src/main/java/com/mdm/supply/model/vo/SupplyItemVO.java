package com.mdm.supply.model.vo;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SupplyItemVO {
    private Long id;
    private String supplyItemCode;
    private String supplyItemName;
    private Integer itemCategory;
    private String specification;
    private String unit;
    private String orgCode;
    private String brand;
    private Integer storageCondition;
    private Integer status;
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
