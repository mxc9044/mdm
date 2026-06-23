package com.mdm.common.core;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class InboundDataDTO implements Serializable {

    @NotBlank(message = "请求唯一ID不能为空")
    private String requestId;

    @NotBlank(message = "数据域不能为空")
    private String domainCode;

    @NotBlank(message = "操作类型不能为空")
    private String operationType;

    private String dataCode;

    private Integer version;

    private String approvalNo;

    private Map<String, Object> payload;

    private String source;
}
