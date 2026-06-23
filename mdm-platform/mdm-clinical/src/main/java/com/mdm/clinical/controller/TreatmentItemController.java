package com.mdm.clinical.controller;

import com.mdm.clinical.model.dto.TreatmentItemDTO;
import com.mdm.clinical.model.query.TreatmentItemQuery;
import com.mdm.clinical.model.vo.TreatmentItemVO;
import com.mdm.clinical.service.TreatmentItemService;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clinical/treatment-items")
@RequiredArgsConstructor
public class TreatmentItemController {

    private final TreatmentItemService treatmentItemService;

    @GetMapping
    public Result<PageResult<TreatmentItemVO>> page(TreatmentItemQuery query) {
        return Result.ok(treatmentItemService.page(query));
    }

    @GetMapping("/{treatmentCode}")
    public Result<TreatmentItemVO> detail(@PathVariable String treatmentCode) {
        return Result.ok(treatmentItemService.getByCode(treatmentCode));
    }

    @PostMapping
    @OperationLog(module = "诊疗项目管理", operation = "新增")
    public Result<TreatmentItemVO> create(@Valid @RequestBody TreatmentItemDTO dto) {
        return Result.ok(treatmentItemService.create(dto));
    }

    @PutMapping("/{treatmentCode}")
    @OperationLog(module = "诊疗项目管理", operation = "更新")
    public Result<TreatmentItemVO> update(@PathVariable String treatmentCode,
                                          @Valid @RequestBody TreatmentItemDTO dto) {
        return Result.ok(treatmentItemService.update(treatmentCode, dto));
    }
}
