package com.mdm.supply.controller;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.supply.model.dto.MaterialDTO;
import com.mdm.supply.model.query.MaterialQuery;
import com.mdm.supply.model.vo.MaterialVO;
import com.mdm.supply.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supply/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public Result<PageResult<MaterialVO>> page(MaterialQuery query) {
        return Result.ok(materialService.page(query));
    }

    @GetMapping("/{materialCode}")
    public Result<MaterialVO> getByCode(@PathVariable String materialCode) {
        return Result.ok(materialService.getByCode(materialCode));
    }

    @PostMapping
    public Result<MaterialVO> create(@Valid @RequestBody MaterialDTO dto) {
        return Result.ok(materialService.create(dto));
    }

    @PutMapping("/{materialCode}")
    public Result<MaterialVO> update(@PathVariable String materialCode, @Valid @RequestBody MaterialDTO dto) {
        return Result.ok(materialService.update(materialCode, dto));
    }
}
