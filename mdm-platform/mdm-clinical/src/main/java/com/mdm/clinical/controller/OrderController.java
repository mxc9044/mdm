package com.mdm.clinical.controller;

import com.mdm.clinical.model.dto.OrderDTO;
import com.mdm.clinical.model.query.OrderQuery;
import com.mdm.clinical.model.vo.OrderVO;
import com.mdm.clinical.service.OrderService;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clinical/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Result<PageResult<OrderVO>> page(OrderQuery query) {
        return Result.ok(orderService.page(query));
    }

    @GetMapping("/{orderCode}")
    public Result<OrderVO> detail(@PathVariable String orderCode) {
        return Result.ok(orderService.getByCode(orderCode));
    }

    @PostMapping
    @OperationLog(module = "医嘱管理", operation = "新增")
    public Result<OrderVO> create(@Valid @RequestBody OrderDTO dto) {
        return Result.ok(orderService.create(dto));
    }

    @PutMapping("/{orderCode}")
    @OperationLog(module = "医嘱管理", operation = "更新")
    public Result<OrderVO> update(@PathVariable String orderCode,
                                  @Valid @RequestBody OrderDTO dto) {
        return Result.ok(orderService.update(orderCode, dto));
    }
}
