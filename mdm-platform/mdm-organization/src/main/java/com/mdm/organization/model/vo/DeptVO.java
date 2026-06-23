package com.mdm.organization.model.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeptVO {
    private Long id;
    private String deptCode;
    private String deptName;
    private String aliasName;
    private Integer deptType;
    private String orgCode;
    private String orgName;
    private String parentCode;
    private String parentName;
    private Integer sortOrder;
    private Integer status;
    private Integer version;
    private LocalDateTime createTime;
    private List<DeptVO> children;
}
