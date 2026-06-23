package com.mdm.audit.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OperationLogVO {
    private Long id;
    private String module;
    private String operation;
    private String method;
    private String requestUrl;
    private Integer responseCode;
    private Long costTime;
    private String operator;
    private String operatorIp;
    private LocalDateTime createTime;
}
