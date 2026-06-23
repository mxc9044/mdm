package com.mdm.supply.model.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true) @TableName("mdm_material")
public class Material extends BaseEntity {
    private String materialCode;
    private String materialName;
    private Integer materialCategory;
    private String specification;
    private String unit;
    private String manufacturer;
    private String regCertNo;
    private String insuranceCode;
    private String orgCode;
    private Integer status;
    @Version private Integer version;
    private String remark;
}
