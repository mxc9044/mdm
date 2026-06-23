package com.mdm.auth.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_api_key")
public class SysApiKey extends BaseEntity {
    private String keyName;
    private String apiKey;
    private String apiSecret;
    private String systemName;
    private String allowedDomains;
    private String allowedOrgs;
    private LocalDateTime expireTime;
    private Integer status;
}
