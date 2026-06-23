package com.mdm.audit.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdm.audit.mapper.OperationLogMapper;
import com.mdm.audit.model.entity.OperationLogEntity;
import com.mdm.audit.model.query.OperationLogQuery;
import com.mdm.audit.model.vo.OperationLogVO;
import com.mdm.common.core.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationLogService {

    private final OperationLogMapper operationLogMapper;

    public PageResult<OperationLogVO> page(OperationLogQuery query) {
        Page<OperationLogEntity> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<OperationLogEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getModule())) {
            wrapper.eq(OperationLogEntity::getModule, query.getModule());
        }
        if (StringUtils.hasText(query.getOperator())) {
            wrapper.like(OperationLogEntity::getOperator, query.getOperator());
        }
        if (StringUtils.hasText(query.getStartTime())) {
            wrapper.ge(OperationLogEntity::getCreateTime, LocalDateTime.parse(query.getStartTime()));
        }
        if (StringUtils.hasText(query.getEndTime())) {
            wrapper.le(OperationLogEntity::getCreateTime, LocalDateTime.parse(query.getEndTime()));
        }
        wrapper.orderByDesc(OperationLogEntity::getCreateTime);
        Page<OperationLogEntity> result = operationLogMapper.selectPage(page, wrapper);
        List<OperationLogVO> voList = result.getRecords().stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, result.getTotal(), query.getPageNum(), query.getPageSize());
    }

    private OperationLogVO toVO(OperationLogEntity entity) {
        OperationLogVO vo = new OperationLogVO();
        vo.setId(entity.getId());
        vo.setModule(entity.getModule());
        vo.setOperation(entity.getOperation());
        vo.setMethod(entity.getMethod());
        vo.setRequestUrl(entity.getRequestUrl());
        vo.setResponseCode(entity.getResponseCode());
        vo.setCostTime(entity.getCostTime());
        vo.setOperator(entity.getOperator());
        vo.setOperatorIp(entity.getOperatorIp());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }
}
