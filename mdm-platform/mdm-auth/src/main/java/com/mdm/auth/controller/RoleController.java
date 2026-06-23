package com.mdm.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdm.auth.mapper.SysRoleMapper;
import com.mdm.auth.model.entity.SysRole;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/system/roles")
@RequiredArgsConstructor
public class RoleController {

    private final SysRoleMapper roleMapper;

    @GetMapping
    public Result<PageResult<SysRole>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        Page<SysRole> page = roleMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysRole>().orderByAsc(SysRole::getId));
        return Result.ok(PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize));
    }
}
