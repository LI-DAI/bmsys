package com.ld.bmsys.auth.service.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ld.bmsys.common.entity.Result;
import com.ld.bmsys.common.enums.ResultCode;
import com.ld.bmsys.common.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.ld.bmsys.common.constant.CommonConstant.AUTHENTICATION;
import static com.ld.bmsys.common.utils.CommonUtil.print;

/**
 * @Author ld
 * @Date 2020/3/7 14:46
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties properties;
    private final StringRedisTemplate redisTemplate;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, SecurityProperties properties, StringRedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.properties = properties;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken(request);
        if (!pathMatcher(request) && StrUtil.isNotBlank(token)) {
            Map<String, Object> tokenMap;
            try {
                tokenMap = JwtTokenUtil.parseJwtToken(token);
            } catch (Exception e) {
                log.error("Parsing Token Exception：", e.getMessage());
                print(Result.fail(ResultCode.EXPIRE_TOKEN), response);
                return;
            }

            String username = MapUtil.getStr(tokenMap, "username");

            String authoritiesStr = redisTemplate.opsForValue().get(username);
            List<SimpleGrantedAuthority> authorities = JSON.parseArray(authoritiesStr, SimpleGrantedAuthority.class);

            if (CollectionUtil.isEmpty(authorities)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                authorities = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();
            }

            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(upToken);
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHENTICATION);
        if (StrUtil.isNotBlank(token) && token.startsWith(properties.getOnlineKey())) {
            return token.substring(properties.getOnlineKey().length()).trim();
        }
        return null;
    }

    public boolean pathMatcher(HttpServletRequest request) {
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : properties.getAnonUri()) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }
}
