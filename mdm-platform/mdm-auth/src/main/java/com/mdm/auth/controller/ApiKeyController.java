package com.mdm.auth.controller;

import com.mdm.auth.model.dto.ApiKeyDTO;
import com.mdm.auth.model.vo.ApiKeyVO;
import com.mdm.auth.service.ApiKeyService;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/system/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @GetMapping
    public Result<PageResult<ApiKeyVO>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        return Result.ok(apiKeyService.page(pageNum, pageSize));
    }

    @PostMapping
    @OperationLog(module = "API Key", operation = "创建")
    public Result<ApiKeyVO> create(@Valid @RequestBody ApiKeyDTO dto) {
        return Result.ok(apiKeyService.create(dto));
    }

    @DeleteMapping("/{id}")
    @OperationLog(module = "API Key", operation = "删除")
    public Result<Void> delete(@PathVariable Long id) {
        apiKeyService.delete(id);
        return Result.ok();
    }

    @PatchMapping("/{id}/status")
    @OperationLog(module = "API Key", operation = "变更状态")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        apiKeyService.updateStatus(id, status);
        return Result.ok();
    }
}
