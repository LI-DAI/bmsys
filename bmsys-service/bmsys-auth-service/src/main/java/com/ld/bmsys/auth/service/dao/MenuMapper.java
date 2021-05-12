package com.ld.bmsys.auth.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ld.bmsys.auth.api.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LD
 * @date 2020/3/5 21:39
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取权限菜单
     *
     * @param userId
     * @return
     */
    List<Menu> getMenuByUserId(@Param("userId") Integer userId);

}