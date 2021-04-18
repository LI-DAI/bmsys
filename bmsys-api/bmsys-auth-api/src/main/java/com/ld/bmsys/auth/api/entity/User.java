package com.ld.bmsys.auth.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

import static com.ld.bmsys.common.constant.CommonConstant.REGEX_PHONE;

/**
 * @Author ld
 * @Date 2020/3/5 13:56
 */
@Data
@ApiModel(value = "用户")
@TableName(value = "bmsys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @NotBlank(message = "username不能为空")
    @ApiModelProperty(value = "姓名")
    private String username;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @Email(message = "邮箱格式异常")
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @Pattern(regexp = REGEX_PHONE, message = "手机号格式异常")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户性别 0男，1女 ")
    private String gender;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "所属公司")
    private String unit;

    @ApiModelProperty(value = "部门")
    private String department;

    @ApiModelProperty(value = "账号状态 0正常，1停用")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "删除标志")
    private Integer enabled;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public User() {
    }

    public User(Integer userId, String username, String nickname, String email, String phone, String gender, String password, String unit, String department, String status, String remark, Integer enabled, Date createTime, Date updateTime) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.unit = unit;
        this.department = department;
        this.status = status;
        this.remark = remark;
        this.enabled = enabled;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

}