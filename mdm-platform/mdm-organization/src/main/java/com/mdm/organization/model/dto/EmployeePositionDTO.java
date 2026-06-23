package com.mdm.organization.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeePositionDTO {
    @NotBlank(message = "任职机构不能为空")
    private String orgCode;
    @NotBlank(message = "任职科室不能为空")
    private String deptCode;
    @NotNull(message = "任职类型不能为空")
    private Integer positionType;
    @NotNull(message = "生效日期不能为空")
    private LocalDate startDate;
    private LocalDate endDate;
}
