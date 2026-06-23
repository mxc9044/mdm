package com.mdm.common.core;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
public class BasePageQuery implements Serializable {

    @Min(value = 1, message = "页码最小为1")
    private int pageNum = 1;

    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 200, message = "每页条数最大为200")
    private int pageSize = MdmConstants.DEFAULT_PAGE_SIZE;

    private String orderBy;
}
