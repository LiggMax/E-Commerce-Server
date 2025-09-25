package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
