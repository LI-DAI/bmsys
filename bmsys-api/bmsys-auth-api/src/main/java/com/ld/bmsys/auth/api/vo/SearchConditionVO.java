package com.ld.bmsys.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ld
 * @Date 2020/4/19 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchConditionVO {

    private String keyword;

    private Integer status;

    private String username;

    private String phone;
}
