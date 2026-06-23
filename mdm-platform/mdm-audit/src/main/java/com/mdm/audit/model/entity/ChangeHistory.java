package com.mdm.audit.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("mdm_change_history")
public class ChangeHistory {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String dataDomain;
    private String dataCode;
    private String changeType;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private Integer version;
    private String approvalNo;
    private String operator;
    private LocalDateTime operateTime;
    private String source;
    private String remark;
}
