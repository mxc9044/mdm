package com.mdm.organization.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDTO {
    @NotBlank(message = "姓名不能为空")
    private String empName;
    private String idCard;
    private String phone;
    private Integer gender;
    private String practiceCert;
    private String titleCert;
    @NotBlank(message = "主岗机构不能为空")
    private String orgCode;
    @NotBlank(message = "主岗科室不能为空")
    private String deptCode;
    private String position;
    private String title;
    private Integer practiceType;
    private Integer status;
    private String remark;
    private Integer version;
    private String approvalNo;
}
