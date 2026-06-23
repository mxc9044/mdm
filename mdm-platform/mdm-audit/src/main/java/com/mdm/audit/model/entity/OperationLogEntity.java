package com.mdm.audit.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_operation_log")
public class OperationLogEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String module;
    private String operation;
    private String method;
    private String requestUrl;
    private String requestParams;
    private Integer responseCode;
    private Long costTime;
    private String operator;
    private String operatorIp;
    private LocalDateTime createTime;
}
