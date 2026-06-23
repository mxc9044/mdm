package com.mdm.organization.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmployeeVO {
    private Long id;
    private String empCode;
    private String empName;
    private Integer gender;
    private String practiceCert;
    private String titleCert;
    private String orgCode;
    private String orgName;
    private String deptCode;
    private String deptName;
    private String position;
    private String title;
    private Integer practiceType;
    private Integer status;
    private Integer version;
    private LocalDateTime createTime;
}
