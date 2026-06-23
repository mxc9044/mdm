package com.mdm.sync.mapping;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MappingVO {
    private Long id;
    private String mdmCode;
    private String mdmName;
    private String localSystem;
    private String localCode;
    private String localName;
    private String dataDomain;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Integer status;
    private LocalDateTime createTime;
}
