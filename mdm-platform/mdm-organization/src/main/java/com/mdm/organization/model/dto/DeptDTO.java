package com.mdm.organization.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeptDTO {
    @NotBlank(message = "科室名称不能为空")
    private String deptName;
    private String aliasName;
    @NotNull(message = "科室类型不能为空")
    private Integer deptType;
    @NotBlank(message = "所属机构不能为空")
    private String orgCode;
    private String parentCode;
    private Integer sortOrder;
    private Integer status;
    private String remark;
    private Integer version;
    private String approvalNo;
}
