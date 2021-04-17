package com.ld.bmsys.auth.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * @Author ld
 * @Date 2020/3/5 21:39
 */
@Data
@TableName(value = "bmsys_role_menu")
@ApiModel(value = "角色菜单")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer roleMenuId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    public RoleMenu() {
    }

    public RoleMenu(Integer roleMenuId, Integer roleId, Integer menuId) {
        this.roleMenuId = roleMenuId;
        this.roleId = roleId;
        this.menuId = menuId;
    }
}

