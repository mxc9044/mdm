package com.mdm.facility.controller;

import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import com.mdm.facility.model.dto.DeviceDTO;
import com.mdm.facility.model.query.DeviceQuery;
import com.mdm.facility.model.vo.DeviceVO;
import com.mdm.facility.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facility/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public Result<PageResult<DeviceVO>> page(DeviceQuery query) {
        return Result.ok(deviceService.page(query));
    }

    @GetMapping("/{deviceCode}")
    public Result<DeviceVO> detail(@PathVariable String deviceCode) {
        return Result.ok(deviceService.getByCode(deviceCode));
    }

    @PostMapping
    @OperationLog(module = "设备管理", operation = "新增")
    public Result<DeviceVO> create(@Valid @RequestBody DeviceDTO dto) {
        return Result.ok(deviceService.create(dto));
    }

    @PutMapping("/{deviceCode}")
    @OperationLog(module = "设备管理", operation = "更新")
    public Result<DeviceVO> update(@PathVariable String deviceCode, @Valid @RequestBody DeviceDTO dto) {
        return Result.ok(deviceService.update(deviceCode, dto));
    }

    @PatchMapping("/{deviceCode}/status")
    @OperationLog(module = "设备管理", operation = "变更状态")
    public Result<Void> updateStatus(@PathVariable String deviceCode, @RequestParam Integer status) {
        deviceService.updateStatus(deviceCode, status);
        return Result.ok();
    }
}
