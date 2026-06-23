package com.mdm.organization.controller;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import com.mdm.organization.model.dto.DeptDTO;
import com.mdm.organization.model.query.DeptQuery;
import com.mdm.organization.model.vo.DeptVO;
import com.mdm.organization.service.DeptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/org/depts")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;

    @GetMapping
    public Result<PageResult<DeptVO>> page(DeptQuery query) {
        return Result.ok(deptService.page(query));
    }

    @GetMapping("/tree/{orgCode}")
    public Result<List<DeptVO>> tree(@PathVariable String orgCode) {
        return Result.ok(deptService.tree(orgCode));
    }

    @GetMapping("/{deptCode}")
    public Result<DeptVO> detail(@PathVariable String deptCode) {
        return Result.ok(deptService.getByCode(deptCode));
    }

    @PostMapping
    @OperationLog(module = "科室管理", operation = "新增")
    public Result<DeptVO> create(@Valid @RequestBody DeptDTO dto) {
        return Result.ok(deptService.create(dto));
    }

    @PutMapping("/{deptCode}")
    @OperationLog(module = "科室管理", operation = "更新")
    public Result<DeptVO> update(@PathVariable String deptCode, @Valid @RequestBody DeptDTO dto) {
        return Result.ok(deptService.update(deptCode, dto));
    }
}
