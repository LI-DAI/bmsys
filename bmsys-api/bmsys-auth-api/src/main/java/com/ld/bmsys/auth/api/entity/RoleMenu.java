package com.ld.bmsys.auth.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

/**
 * @Author ld
 * @Date 2020/3/5 21:39
 */
@Data
@ApiModel(value = "com.ld.bmsys.auth.api.entity.RoleMenu")
public class RoleMenu implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer roleMenuId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    private static final long serialVersionUID = 1L;
}

