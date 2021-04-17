package com.ld.bmsys.auth.service.dao;

import com.ld.bmsys.auth.api.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ld
 * @Date 2020/3/5 21:39
 */
public interface RoleMapper {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insertSelective(Role record);

    /**
     * 获取
     *
     * @param roleId
     * @return
     */
    Role selectByPrimaryKey(Integer roleId);

    /**
     * 修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * 获取roles
     *
     * @return
     */
    List<Role> getRolesByUserId(@Param("userId") Integer userId);
}