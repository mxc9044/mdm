package com.mdm.clinical.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StandardVO {

    private Long id;

    private String stdCode;

    private String stdName;

    private Integer stdType;

    private String bizVersion;

    private LocalDate effectiveDate;

    private String orgCode;

    private Integer status;

    private Integer version;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
