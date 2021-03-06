package com.ld.bmsys.auth.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ld.bmsys.auth.api.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LD
 * @date 2020/3/5 21:39
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取roles
     *
     * @param userId /
     * @return /
     */
    List<Role> getRolesByUserId(@Param("userId") Integer userId);
}