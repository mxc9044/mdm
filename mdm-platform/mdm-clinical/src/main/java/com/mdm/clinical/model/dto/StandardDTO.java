package com.mdm.clinical.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StandardDTO {

    @NotBlank(message = "规范名称不能为空")
    private String stdName;

    private Integer stdType;

    private String bizVersion;

    private LocalDate effectiveDate;

    private String orgCode;

    private Integer status;

    private String remark;

    private Integer version;

    private String approvalNo;
}
