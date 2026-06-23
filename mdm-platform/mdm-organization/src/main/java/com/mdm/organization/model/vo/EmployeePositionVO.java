package com.mdm.organization.model.vo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeePositionVO {
    private Long id;
    private String empCode;
    private String orgCode;
    private String orgName;
    private String deptCode;
    private String deptName;
    private Integer positionType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
}
