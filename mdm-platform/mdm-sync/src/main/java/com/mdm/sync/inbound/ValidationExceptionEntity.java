package com.mdm.sync.inbound;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mdm_validation_exception")
public class ValidationExceptionEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String dataDomain;
    private String dataCode;
    private String requestId;
    private String exceptionType;
    private String exceptionDetail;
    private String rawData;
    private Integer handleStatus;
    private String handleBy;
    private LocalDateTime handleTime;
    private String handleRemark;
    private LocalDateTime createTime;
}
