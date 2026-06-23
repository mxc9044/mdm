package com.mdm.clinical.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderVO {

    private Long id;

    private String orderCode;

    private String orderName;

    private Integer orderType;

    private Integer orderCategory;

    private String frequency;

    private String orgCode;

    private Integer status;

    private Integer version;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<OrderItemVO> items;

    private List<String> deptCodes;

    @Data
    public static class OrderItemVO {
        private Long id;
        private Integer itemType;
        private String itemCode;
        private Integer sortOrder;
    }
}
