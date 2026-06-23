package com.mdm.clinical.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_order")
public class Order extends BaseEntity {

    private String orderCode;

    private String orderName;

    private Integer orderType;

    private Integer orderCategory;

    private String frequency;

    private String orgCode;

    private Integer status;

    @Version
    private Integer version;

    private String remark;
}
