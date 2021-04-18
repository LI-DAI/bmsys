package com.ld.bmsys.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ld.bmsys.common.entity.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author ld
 * @Date 2020/3/11 13:52
 */
public class CommonUtil {

    public static void print(Result<Object> result, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.println(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
