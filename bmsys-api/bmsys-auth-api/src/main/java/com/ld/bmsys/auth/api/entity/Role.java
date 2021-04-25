package com.ld.bmsys.auth.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LD
 * @date 2020/3/5 13:56
 */
@Data
@TableName(value = "bmsys_role")
@ApiModel(value = "角色")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty(value = "角色名称(中文)")
    private String roleName;

    @NotBlank(message = "角色KEY不能为空")
    @ApiModelProperty(value = "角色字符串")
    private String roleKey;

    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;

    @ApiModelProperty(value = "角色状态：0正常，1停用")
    private String status;

    @ApiModelProperty(value = "角色类型：G角色组，R角色")
    private String roleType;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    public Role() {
    }

    public Role(Integer roleId, String roleName, String roleKey, Integer roleSort, String status, String roleType, Integer parentId, String createBy, Date createTime, String updateBy, Date updateTime, String remark) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleKey = roleKey;
        this.roleSort = roleSort;
        this.status = status;
        this.roleType = roleType;
        this.parentId = parentId;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }
}

