package com.mdm.clinical.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_standard")
public class Standard extends BaseEntity {

    private String stdCode;

    private String stdName;

    private Integer stdType;

    private String bizVersion;

    private LocalDate effectiveDate;

    private String orgCode;

    private Integer status;

    @Version
    private Integer version;

    private String remark;
}
