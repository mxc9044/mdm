package com.mdm.audit.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChangeHistoryVO {
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
}
