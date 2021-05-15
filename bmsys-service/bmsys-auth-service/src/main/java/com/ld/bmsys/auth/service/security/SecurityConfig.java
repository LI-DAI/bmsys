package com.ld.bmsys.auth.service.security;

import cn.hutool.core.convert.Convert;
import com.ld.bmsys.auth.service.security.anon.AnonymousAccessProcess;
import com.ld.bmsys.auth.service.security.url.UrlAccessDecisionManager;
import com.ld.bmsys.auth.service.security.url.UrlFilterInvocationSecurityMetadataSource;
import com.ld.bmsys.auth.service.service.MenuService;
import com.ld.bmsys.common.entity.Result;
import com.ld.bmsys.common.enums.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Set;

import static com.ld.bmsys.auth.service.security.anon.AnonymousAccessProcess.anonymousCache;
import static com.ld.bmsys.common.constant.CommonConstant.ANON_CACHE_KEY;
import static com.ld.bmsys.common.utils.CommonUtil.print;

/**
 * @author LD
 * @date 2020/3/5 16:53
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final SecurityProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final MenuService menuService;

    public SecurityConfig(SecurityProperties properties, UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder, RequestMappingHandlerMapping requestMappingHandlerMapping, MenuService menuService) {
        this.properties = properties;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.menuService = menuService;
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
        AnonymousAccessProcess.loadAnonymousAccessProcess(requestMappingHandlerMapping, properties);
        http
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsServiceImpl, properties), UsernamePasswordAuthenticationFilter.class)
                //禁用csrf,
                .csrf().disable()
                //关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //支持跨域
                .cors()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .withObjectPostProcessor(objectPostProcessor())
                .and()
                .exceptionHandling()
                //未登录处理
                .authenticationEntryPoint((request, response, e) -> print(Result.fail(ResultCode.UN_AUTHENTICATION), response))
                //无权限处理
                .accessDeniedHandler((request, response, e) -> print(Result.fail(ResultCode.UN_AUTHORIZATION), response));
    }


    public ObjectPostProcessor<FilterSecurityInterceptor> objectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource(menuService));
                object.setAccessDecisionManager(urlAccessDecisionManager());
                return object;
            }
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //自定义过滤器资源放行，所有过滤器都不执行(permitAll放行时仍然会执行过滤器)
        web.ignoring().antMatchers(anonymousAccess())
                .antMatchers("/websocket/**")
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource(MenuService menuService) {
        return new UrlFilterInvocationSecurityMetadataSource(menuService);
    }

    @Bean
    public UrlAccessDecisionManager urlAccessDecisionManager() {
        return new UrlAccessDecisionManager();
    }

    public String[] anonymousAccess() {
        Set<String> cacheUri = anonymousCache.getIfPresent(ANON_CACHE_KEY);
        return Convert.toStrArray(cacheUri);
    }

}
