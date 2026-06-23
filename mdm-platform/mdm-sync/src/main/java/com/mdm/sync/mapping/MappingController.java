package com.mdm.sync.mapping;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sync/mappings")
@RequiredArgsConstructor
public class MappingController {

    private final MappingService mappingService;

    @GetMapping
    public Result<PageResult<MappingVO>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String dataDomain,
            @RequestParam(required = false) String localSystem,
            @RequestParam(required = false) String keyword) {
        return Result.ok(mappingService.page(pageNum, pageSize, dataDomain, localSystem, keyword));
    }

    @PostMapping
    @OperationLog(module = "对照映射", operation = "新增")
    public Result<MappingVO> create(@Valid @RequestBody MappingDTO dto) {
        return Result.ok(mappingService.create(dto));
    }

    @PutMapping("/{id}")
    @OperationLog(module = "对照映射", operation = "更新")
    public Result<MappingVO> update(@PathVariable Long id, @Valid @RequestBody MappingDTO dto) {
        return Result.ok(mappingService.update(id, dto));
    }

    @GetMapping("/translate")
    public Result<String> translate(@RequestParam String mdmCode, @RequestParam String localSystem) {
        return Result.ok(mappingService.translate(mdmCode, localSystem));
    }
}
