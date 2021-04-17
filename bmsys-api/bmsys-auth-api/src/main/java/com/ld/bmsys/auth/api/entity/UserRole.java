package com.ld.bmsys.auth.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ld
 * @Date 2020/3/5 21:39
 */
@Data
@ApiModel(value = "com.ld.bmsys.auth.api.entity.UserRole")
public class UserRole implements Serializable {

    @ApiModelProperty(value = "ID")
    private Integer userRoleId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}

