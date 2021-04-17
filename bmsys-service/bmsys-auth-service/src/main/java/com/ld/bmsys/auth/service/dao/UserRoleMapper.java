package com.ld.bmsys.auth.service.dao;

import com.ld.bmsys.auth.api.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/5 21:39
 */
public interface UserRoleMapper {

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<UserRole> list);

    int deleteUserRoleByUserId(@Param("userIds") List<Integer> userIds);

    int deleteUserRoleByRoleId(@Param("roleIds") List<Integer> roleIds);
}