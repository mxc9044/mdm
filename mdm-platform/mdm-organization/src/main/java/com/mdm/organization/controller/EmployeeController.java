package com.mdm.organization.controller;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import com.mdm.organization.model.dto.EmployeeDTO;
import com.mdm.organization.model.dto.EmployeePositionDTO;
import com.mdm.organization.model.query.EmployeeQuery;
import com.mdm.organization.model.vo.EmployeePositionVO;
import com.mdm.organization.model.vo.EmployeeVO;
import com.mdm.organization.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/org/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public Result<PageResult<EmployeeVO>> page(EmployeeQuery query) {
        return Result.ok(employeeService.page(query));
    }

    @GetMapping("/{empCode}")
    public Result<EmployeeVO> detail(@PathVariable String empCode) {
        return Result.ok(employeeService.getByCode(empCode));
    }

    @PostMapping
    @OperationLog(module = "员工管理", operation = "新增")
    public Result<EmployeeVO> create(@Valid @RequestBody EmployeeDTO dto) {
        return Result.ok(employeeService.create(dto));
    }

    @PutMapping("/{empCode}")
    @OperationLog(module = "员工管理", operation = "更新")
    public Result<EmployeeVO> update(@PathVariable String empCode, @Valid @RequestBody EmployeeDTO dto) {
        return Result.ok(employeeService.update(empCode, dto));
    }

    @GetMapping("/{empCode}/positions")
    public Result<List<EmployeePositionVO>> positions(@PathVariable String empCode) {
        return Result.ok(employeeService.getPositions(empCode));
    }

    @PostMapping("/{empCode}/positions")
    @OperationLog(module = "员工管理", operation = "新增任职")
    public Result<Void> addPosition(@PathVariable String empCode, @Valid @RequestBody EmployeePositionDTO dto) {
        employeeService.addPosition(empCode, dto);
        return Result.ok();
    }
}
