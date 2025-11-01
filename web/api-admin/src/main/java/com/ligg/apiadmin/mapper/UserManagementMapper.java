package com.ligg.apiadmin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.apiadmin.pojo.vo.UserManagementVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Ligg
 * @Time 2025/10/28
 **/
@Mapper
public interface UserManagementMapper {
    IPage<UserManagementVo> selectUserList(IPage<UserManagementVo> page, String search);

    void updateUserStatus(String userId, Boolean status);
}
