package com.ld.bmsys.auth.service.security.url;

import com.ld.bmsys.auth.api.entity.Menu;
import com.ld.bmsys.auth.service.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.stream.Collectors;

import static com.ld.bmsys.auth.service.security.anon.AnonymousAccessProcess.anonymousCache;
import static com.ld.bmsys.common.constant.CommonConstant.ANON_CACHE_KEY;

/**
 * @author LD
 * @date 2021/5/11 17:07
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final MenuService menuService;

    public UrlFilterInvocationSecurityMetadataSource(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        FilterInvocation invocation = (FilterInvocation) object;

        String requestUrl = invocation.getRequestUrl();
        Set<String> anonCachePattern = anonymousCache.getIfPresent(ANON_CACHE_KEY);
        for (String pattern : anonCachePattern) {
            //匿名访问
            if (pathMatcher.match(pattern, requestUrl)) return null;
        }

        Map<String, String> requestMap = requestMap();
        for (Map.Entry<String, String> entry : requestMap.entrySet()) {
            if (pathMatcher.match(requestUrl, entry.getKey())) {
                return SecurityConfig.createList(entry.getValue());
            }
        }

        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public Map<String, String> requestMap() {
        List<Menu> menus = menuService.list();
        return menus.stream().filter(Objects::nonNull).filter(menu -> !menu.getUrl().equals("#"))
                .collect(Collectors.toMap(Menu::getUrl, Menu::getPerms));
    }
}
