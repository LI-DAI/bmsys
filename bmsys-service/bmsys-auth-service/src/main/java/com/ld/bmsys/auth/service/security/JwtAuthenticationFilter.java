package com.ld.bmsys.auth.service.security;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.ld.bmsys.common.entity.Result;
import com.ld.bmsys.common.enums.ResultCode;
import com.ld.bmsys.common.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;

import static com.ld.bmsys.auth.service.security.anon.AnonymousAccessProcess.anonymousCache;
import static com.ld.bmsys.common.constant.CommonConstant.ANON_CACHE_KEY;
import static com.ld.bmsys.common.constant.CommonConstant.AUTHENTICATION;
import static com.ld.bmsys.common.utils.CommonUtil.print;

/**
 * @author LD
 * @date 2020/3/7 14:46
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties properties;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, SecurityProperties properties) {
        this.userDetailsService = userDetailsService;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken(request);
        if (StrUtil.isNotBlank(token)) {
            Map<String, Object> tokenMap;
            try {
                tokenMap = JwtTokenUtil.parseJwtToken(token);
            } catch (Exception e) {
                log.error("Parsing Token Exception???{}", e.getMessage());
                print(Result.fail(ResultCode.EXPIRE_TOKEN), response);
                return;
            }

            String username = MapUtil.getStr(tokenMap, "username");

            //???user??????????????????????????????token????????????????????????jwt??????
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            List<SimpleGrantedAuthority> authorities = Convert.toList(SimpleGrantedAuthority.class, userDetails.getAuthorities());

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
        //???????????????????????????
        PathMatcher pathMatcher = new AntPathMatcher();
        Set<String> cacheUri = anonymousCache.getIfPresent(ANON_CACHE_KEY);
        for (String pattern : cacheUri) {
            if (pathMatcher.match(pattern, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }
}
