package com.mdm.sync.inbound;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/sync/exceptions")
@RequiredArgsConstructor
public class SyncExceptionController {

    private final ValidationExceptionMapper exceptionMapper;

    @GetMapping
    public Result<PageResult<ValidationExceptionEntity>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String dataDomain,
            @RequestParam(required = false) Integer handleStatus) {
        Page<ValidationExceptionEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ValidationExceptionEntity> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dataDomain)) w.eq(ValidationExceptionEntity::getDataDomain, dataDomain);
        if (handleStatus != null) w.eq(ValidationExceptionEntity::getHandleStatus, handleStatus);
        w.orderByDesc(ValidationExceptionEntity::getCreateTime);
        Page<ValidationExceptionEntity> result = exceptionMapper.selectPage(page, w);
        return Result.ok(PageResult.of(result.getRecords(), result.getTotal(), pageNum, pageSize));
    }

    @PatchMapping("/{id}/handle")
    public Result<Void> handle(@PathVariable Long id, @RequestParam Integer handleStatus, @RequestParam(required = false) String remark) {
        ValidationExceptionEntity entity = exceptionMapper.selectById(id);
        if (entity != null) {
            entity.setHandleStatus(handleStatus);
            entity.setHandleRemark(remark);
            entity.setHandleTime(LocalDateTime.now());
            exceptionMapper.updateById(entity);
        }
        return Result.ok();
    }
}
