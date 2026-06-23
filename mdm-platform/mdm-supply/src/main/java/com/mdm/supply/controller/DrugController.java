package com.mdm.supply.controller;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.supply.model.dto.DrugDTO;
import com.mdm.supply.model.query.DrugQuery;
import com.mdm.supply.model.vo.DrugVO;
import com.mdm.supply.service.DrugService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supply/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @GetMapping
    public Result<PageResult<DrugVO>> page(DrugQuery query) {
        return Result.ok(drugService.page(query));
    }

    @GetMapping("/{drugCode}")
    public Result<DrugVO> getByCode(@PathVariable String drugCode) {
        return Result.ok(drugService.getByCode(drugCode));
    }

    @GetMapping("/search")
    public Result<PageResult<DrugVO>> search(@RequestParam String keyword) {
        DrugQuery query = new DrugQuery();
        query.setKeyword(keyword);
        return Result.ok(drugService.page(query));
    }

    @PostMapping
    public Result<DrugVO> create(@Valid @RequestBody DrugDTO dto) {
        return Result.ok(drugService.create(dto));
    }

    @PutMapping("/{drugCode}")
    public Result<DrugVO> update(@PathVariable String drugCode, @Valid @RequestBody DrugDTO dto) {
        return Result.ok(drugService.update(drugCode, dto));
    }
}
