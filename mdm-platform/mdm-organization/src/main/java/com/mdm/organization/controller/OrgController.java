package com.mdm.organization.controller;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import com.mdm.organization.model.dto.OrgDTO;
import com.mdm.organization.model.query.OrgQuery;
import com.mdm.organization.model.vo.OrgVO;
import com.mdm.organization.service.OrgService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/org/orgs")
@RequiredArgsConstructor
public class OrgController {

    private final OrgService orgService;

    @GetMapping
    public Result<PageResult<OrgVO>> page(OrgQuery query) {
        return Result.ok(orgService.page(query));
    }

    @GetMapping("/tree")
    public Result<List<OrgVO>> tree() {
        return Result.ok(orgService.tree());
    }

    @GetMapping("/{orgCode}")
    public Result<OrgVO> detail(@PathVariable String orgCode) {
        return Result.ok(orgService.getByCode(orgCode));
    }

    @PostMapping
    @OperationLog(module = "机构管理", operation = "新增")
    public Result<OrgVO> create(@Valid @RequestBody OrgDTO dto) {
        return Result.ok(orgService.create(dto));
    }

    @PutMapping("/{orgCode}")
    @OperationLog(module = "机构管理", operation = "更新")
    public Result<OrgVO> update(@PathVariable String orgCode, @Valid @RequestBody OrgDTO dto) {
        return Result.ok(orgService.update(orgCode, dto));
    }

    @PatchMapping("/{orgCode}/status")
    @OperationLog(module = "机构管理", operation = "变更状态")
    public Result<Void> updateStatus(@PathVariable String orgCode, @RequestParam Integer status) {
        orgService.updateStatus(orgCode, status);
        return Result.ok();
    }
}
