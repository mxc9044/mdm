package com.mdm.sync.mapping;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MappingDTO {
    @NotBlank(message = "主数据编码不能为空")
    private String mdmCode;
    private String mdmName;
    @NotBlank(message = "本地系统标识不能为空")
    private String localSystem;
    @NotBlank(message = "本地编码不能为空")
    private String localCode;
    private String localName;
    @NotBlank(message = "数据域不能为空")
    private String dataDomain;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Integer status;
}
