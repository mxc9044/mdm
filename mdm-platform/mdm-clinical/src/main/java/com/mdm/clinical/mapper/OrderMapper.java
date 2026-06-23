package com.mdm.clinical.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdm.clinical.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
