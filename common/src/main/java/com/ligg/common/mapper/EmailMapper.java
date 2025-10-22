package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.module.entity.EmailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author LiGG
 * @Time 2025/10/8
 */
@Mapper
public interface EmailMapper extends BaseMapper<EmailEntity> {
}
