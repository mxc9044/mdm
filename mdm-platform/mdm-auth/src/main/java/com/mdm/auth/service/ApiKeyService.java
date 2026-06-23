package com.mdm.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdm.auth.mapper.SysApiKeyMapper;
import com.mdm.auth.model.dto.ApiKeyDTO;
import com.mdm.auth.model.entity.SysApiKey;
import com.mdm.auth.model.vo.ApiKeyVO;
import com.mdm.common.core.PageResult;
import com.mdm.common.exception.BizException;
import com.mdm.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final SysApiKeyMapper apiKeyMapper;

    public PageResult<ApiKeyVO> page(int pageNum, int pageSize) {
        Page<SysApiKey> page = new Page<>(pageNum, pageSize);
        Page<SysApiKey> result = apiKeyMapper.selectPage(page,
                new LambdaQueryWrapper<SysApiKey>().orderByDesc(SysApiKey::getCreateTime));
        List<ApiKeyVO> voList = result.getRecords().stream().map(this::toVO).collect(Collectors.toList());
        return PageResult.of(voList, result.getTotal(), pageNum, pageSize);
    }

    public ApiKeyVO create(ApiKeyDTO dto) {
        SysApiKey entity = new SysApiKey();
        entity.setKeyName(dto.getKeyName());
        entity.setApiKey(UUID.randomUUID().toString().replace("-", ""));
        entity.setApiSecret(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));
        entity.setSystemName(dto.getSystemName());
        entity.setAllowedDomains(dto.getAllowedDomains());
        entity.setAllowedOrgs(dto.getAllowedOrgs());
        entity.setExpireTime(dto.getExpireTime());
        entity.setStatus(1);
        apiKeyMapper.insert(entity);
        return toVO(entity);
    }

    public void delete(Long id) {
        apiKeyMapper.deleteById(id);
    }

    public void updateStatus(Long id, Integer status) {
        SysApiKey entity = apiKeyMapper.selectById(id);
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "API Key 不存在");
        }
        entity.setStatus(status);
        apiKeyMapper.updateById(entity);
    }

    public boolean validateApiKey(String apiKey) {
        SysApiKey entity = apiKeyMapper.selectOne(
                new LambdaQueryWrapper<SysApiKey>().eq(SysApiKey::getApiKey, apiKey));
        if (entity == null || entity.getStatus() != 1) {
            return false;
        }
        if (entity.getExpireTime() != null && entity.getExpireTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    private ApiKeyVO toVO(SysApiKey entity) {
        ApiKeyVO vo = new ApiKeyVO();
        vo.setId(entity.getId());
        vo.setKeyName(entity.getKeyName());
        vo.setApiKey(entity.getApiKey());
        vo.setSystemName(entity.getSystemName());
        vo.setAllowedDomains(entity.getAllowedDomains());
        vo.setAllowedOrgs(entity.getAllowedOrgs());
        vo.setExpireTime(entity.getExpireTime());
        vo.setStatus(entity.getStatus());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }
}
