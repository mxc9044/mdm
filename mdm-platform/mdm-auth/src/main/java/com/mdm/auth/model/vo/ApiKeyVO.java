package com.mdm.auth.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiKeyVO {
    private Long id;
    private String keyName;
    private String apiKey;
    private String systemName;
    private String allowedDomains;
    private String allowedOrgs;
    private LocalDateTime expireTime;
    private Integer status;
    private LocalDateTime createTime;
}
