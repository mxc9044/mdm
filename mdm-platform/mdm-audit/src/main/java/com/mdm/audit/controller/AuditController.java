package com.mdm.audit.controller;

import com.mdm.audit.model.query.ChangeHistoryQuery;
import com.mdm.audit.model.query.OperationLogQuery;
import com.mdm.audit.model.vo.ChangeHistoryVO;
import com.mdm.audit.model.vo.OperationLogVO;
import com.mdm.audit.service.ChangeHistoryService;
import com.mdm.audit.service.OperationLogService;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {

    private final ChangeHistoryService changeHistoryService;
    private final OperationLogService operationLogService;

    @GetMapping("/changes")
    public Result<PageResult<ChangeHistoryVO>> changes(ChangeHistoryQuery query) {
        return Result.ok(changeHistoryService.page(query));
    }

    @GetMapping("/changes/{dataCode}")
    public Result<List<ChangeHistoryVO>> changesByCode(@PathVariable String dataCode) {
        return Result.ok(changeHistoryService.getByDataCode(dataCode));
    }

    @GetMapping("/operations")
    public Result<PageResult<OperationLogVO>> operations(OperationLogQuery query) {
        return Result.ok(operationLogService.page(query));
    }
}
