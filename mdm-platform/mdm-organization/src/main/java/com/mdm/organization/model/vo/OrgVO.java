package com.mdm.organization.model.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrgVO {
    private Long id;
    private String orgCode;
    private String orgName;
    private String shortName;
    private Integer orgType;
    private String parentCode;
    private String parentName;
    private String regionCode;
    private String address;
    private String contactPhone;
    private String licenseNo;
    private Integer status;
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrgVO> children;
}
