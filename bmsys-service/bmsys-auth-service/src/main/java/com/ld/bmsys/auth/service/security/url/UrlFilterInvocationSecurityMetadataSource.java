package com.ld.bmsys.auth.service.security.url;

import com.ld.bmsys.auth.api.entity.Menu;
import com.ld.bmsys.auth.service.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Map<String, String> requestMap = requestMap();
        for (Map.Entry<String, String> entry : requestMap.entrySet()) {
            if (pathMatcher.match(requestUrl, entry.getKey())) {
                return SecurityConfig.createList(entry.getValue());
            }
        }

        return null;
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
