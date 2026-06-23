package com.mdm.supply.controller;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.supply.model.dto.SupplyItemDTO;
import com.mdm.supply.model.query.SupplyItemQuery;
import com.mdm.supply.model.vo.SupplyItemVO;
import com.mdm.supply.service.SupplyItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supply/supply-items")
@RequiredArgsConstructor
public class SupplyItemController {

    private final SupplyItemService supplyItemService;

    @GetMapping
    public Result<PageResult<SupplyItemVO>> page(SupplyItemQuery query) {
        return Result.ok(supplyItemService.page(query));
    }

    @GetMapping("/{supplyItemCode}")
    public Result<SupplyItemVO> getByCode(@PathVariable String supplyItemCode) {
        return Result.ok(supplyItemService.getByCode(supplyItemCode));
    }

    @PostMapping
    public Result<SupplyItemVO> create(@Valid @RequestBody SupplyItemDTO dto) {
        return Result.ok(supplyItemService.create(dto));
    }

    @PutMapping("/{supplyItemCode}")
    public Result<SupplyItemVO> update(@PathVariable String supplyItemCode, @Valid @RequestBody SupplyItemDTO dto) {
        return Result.ok(supplyItemService.update(supplyItemCode, dto));
    }
}
