package com.mdm.clinical.controller;

import com.mdm.clinical.model.dto.StandardDTO;
import com.mdm.clinical.model.query.StandardQuery;
import com.mdm.clinical.model.vo.StandardVO;
import com.mdm.clinical.service.StandardService;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clinical/standards")
@RequiredArgsConstructor
public class StandardController {

    private final StandardService standardService;

    @GetMapping
    public Result<PageResult<StandardVO>> page(StandardQuery query) {
        return Result.ok(standardService.page(query));
    }

    @GetMapping("/{stdCode}")
    public Result<StandardVO> detail(@PathVariable String stdCode) {
        return Result.ok(standardService.getByCode(stdCode));
    }

    @PostMapping
    @OperationLog(module = "规范管理", operation = "新增")
    public Result<StandardVO> create(@Valid @RequestBody StandardDTO dto) {
        return Result.ok(standardService.create(dto));
    }

    @PutMapping("/{stdCode}")
    @OperationLog(module = "规范管理", operation = "更新")
    public Result<StandardVO> update(@PathVariable String stdCode,
                                     @Valid @RequestBody StandardDTO dto) {
        return Result.ok(standardService.update(stdCode, dto));
    }
}
