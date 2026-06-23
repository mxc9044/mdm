package com.mdm.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdm.audit.model.entity.OperationLogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLogEntity> {
}
