package com.mdm.sync.mapping.model;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_code_mapping")
public class CodeMapping extends BaseEntity {
    private String mdmCode;
    private String mdmName;
    private String localSystem;
    private String localCode;
    private String localName;
    private String dataDomain;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Integer status;
}
