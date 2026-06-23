package com.mdm.clinical.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mdm_order_dept")
public class OrderDept {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String orderCode;

    private String deptCode;

    private LocalDateTime createTime;
}
