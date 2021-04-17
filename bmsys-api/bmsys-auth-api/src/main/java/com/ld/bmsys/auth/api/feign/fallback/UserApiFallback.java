package com.ld.bmsys.auth.api.feign.fallback;

import com.ld.bmsys.auth.api.entity.User;
import com.ld.bmsys.auth.api.feign.UserApi;
import com.ld.bmsys.common.entity.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author ld
 * @Date 2020/3/5 17:13
 */
@Component
public class UserApiFallback implements FallbackFactory<UserApi> {

    @Override
    public UserApi create(Throwable throwable) {
        String message = throwable.getMessage();
        return new UserApi() {
            @Override
            public Result<User> findUserByUsername(String username) {
                return Result.fail(message);
            }
        };
    }
}
