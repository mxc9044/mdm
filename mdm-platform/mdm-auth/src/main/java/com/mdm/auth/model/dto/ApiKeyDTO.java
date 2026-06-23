package com.mdm.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiKeyDTO {
    @NotBlank(message = "Key名称不能为空")
    private String keyName;
    @NotBlank(message = "系统名称不能为空")
    private String systemName;
    private String allowedDomains;
    private String allowedOrgs;
    private LocalDateTime expireTime;
}
