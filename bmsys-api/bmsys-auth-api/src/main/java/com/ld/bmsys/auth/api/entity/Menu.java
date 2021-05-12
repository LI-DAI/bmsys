package com.ld.bmsys.auth.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author LD
 * @date 2020/3/5 21:39
 */
@Data
@TableName(value = "bmsys_menu")
@ApiModel(value = "菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private String visible;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(exist = false)
    private List<Menu> children;

    public Menu() {
    }

    public Menu(Integer menuId, String menuName, Integer parentId, Integer orderNum, String url, String menuType, String visible, String perms, String remark) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.parentId = parentId;
        this.orderNum = orderNum;
        this.url = url;
        this.menuType = menuType;
        this.visible = visible;
        this.perms = perms;
        this.remark = remark;
    }
}

