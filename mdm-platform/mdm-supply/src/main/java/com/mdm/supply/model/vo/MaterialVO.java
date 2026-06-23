package com.mdm.supply.model.vo;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialVO {
    private Long id;
    private String materialCode;
    private String materialName;
    private Integer materialCategory;
    private String specification;
    private String unit;
    private String manufacturer;
    private String regCertNo;
    private String insuranceCode;
    private String orgCode;
    private Integer status;
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
