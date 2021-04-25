package com.ld.bmsys.auth.api.feign;

import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.feign.fallback.UserApiFallback;
import com.ld.bmsys.common.constant.CommonConstant;
import com.ld.bmsys.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author LD
 * @date 2020/3/5 17:10
 */
@FeignClient(name = CommonConstant.AUTH_SERVICE_NAME, fallbackFactory = UserApiFallback.class)
@RequestMapping("/user")
public interface UserApi {

    /**
     * 获取用户
     *
     * @param username
     * @return
     */
    @GetMapping("/find")
    Result<User> findUserByUsername(@RequestParam("username") String username);
}
