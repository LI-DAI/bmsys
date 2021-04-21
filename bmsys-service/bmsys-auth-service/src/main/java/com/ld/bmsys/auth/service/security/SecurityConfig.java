package com.ld.bmsys.auth.service.security;

import com.ld.bmsys.common.entity.Result;
import com.ld.bmsys.common.enums.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static com.ld.bmsys.common.utils.CommonUtil.print;

/**
 * @Author ld
 * @Date 2020/3/5 16:53
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final SecurityProperties properties;
    private final StringRedisTemplate redisTemplate;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(StringRedisTemplate redisTemplate, SecurityProperties properties, UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsServiceImpl, properties, redisTemplate), UsernamePasswordAuthenticationFilter.class)
                //禁用csrf,
                .csrf().disable()
                //关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //支持跨域
                .cors()
                .and()
                .authorizeRequests()
                //匿名访问
                .antMatchers(anonymousAccess()).permitAll()
                .antMatchers("/websocket/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                //未登录处理
                .authenticationEntryPoint((request, response, e) -> print(Result.fail(ResultCode.UN_AUTHENTICATION), response))
                //无权限处理
                .accessDeniedHandler((request, response, e) -> print(Result.fail(ResultCode.UN_AUTHORIZATION), response));
    }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //自定义过滤器资源放行，所有过滤器都不执行(permitAll放行时仍然会执行过滤器)
//        web.ignoring().antMatchers(anonymousAccess()).antMatchers(HttpMethod.OPTIONS, "/**");
//    }


    public String[] anonymousAccess() {
        List<String> anonList = properties.getAnonUri();
        return anonList.toArray(new String[0]);
    }

}
