package com.mdm.clinical.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mdm_order_item")
public class OrderItem {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String orderCode;

    private Integer itemType;

    private String itemCode;

    private Integer sortOrder;

    private LocalDateTime createTime;
}
