package com.mdm.facility.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DeviceDTO {
    @NotBlank(message = "设备名称不能为空")
    private String deviceName;
    private Integer deviceCategory;
    private String brand;
    private String specification;
    @NotBlank(message = "所属机构不能为空")
    private String orgCode;
    private String deptCode;
    private String assetNo;
    private String regCertNo;
    private LocalDate purchaseDate;
    private Integer serviceLife;
    private LocalDate warrantyExpire;
    private Integer status;
    private String remark;
    private Integer version;
    private String approvalNo;
}
