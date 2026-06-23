package com.mdm.auth.controller;

import com.mdm.auth.model.dto.UserDTO;
import com.mdm.auth.model.vo.UserVO;
import com.mdm.auth.service.UserService;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.common.log.OperationLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/system/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Result<PageResult<UserVO>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.ok(userService.page(pageNum, pageSize, keyword));
    }

    @PostMapping
    @OperationLog(module = "用户管理", operation = "新增用户")
    public Result<Void> create(@Valid @RequestBody UserDTO dto) {
        userService.create(dto);
        return Result.ok();
    }

    @PutMapping("/{id}")
    @OperationLog(module = "用户管理", operation = "更新用户")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        userService.update(id, dto);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    @OperationLog(module = "用户管理", operation = "删除用户")
    public Result<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.ok();
    }
}
