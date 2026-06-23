package com.mdm.clinical.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    @NotBlank(message = "医嘱名称不能为空")
    private String orderName;

    private Integer orderType;

    private Integer orderCategory;

    private String frequency;

    private String orgCode;

    private Integer status;

    private String remark;

    private Integer version;

    private String approvalNo;

    private List<OrderItemDTO> items;

    private List<String> deptCodes;

    @Data
    public static class OrderItemDTO {
        private Integer itemType;
        private String itemCode;
        private Integer sortOrder;
    }
}
