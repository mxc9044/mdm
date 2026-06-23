package com.mdm.facility.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DeviceVO {
    private Long id;
    private String deviceCode;
    private String deviceName;
    private Integer deviceCategory;
    private String brand;
    private String specification;
    private String orgCode;
    private String orgName;
    private String deptCode;
    private String deptName;
    private String assetNo;
    private String regCertNo;
    private LocalDate purchaseDate;
    private Integer serviceLife;
    private LocalDate warrantyExpire;
    private Integer status;
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
