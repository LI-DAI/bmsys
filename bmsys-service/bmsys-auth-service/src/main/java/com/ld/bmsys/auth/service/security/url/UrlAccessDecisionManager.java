package com.ld.bmsys.auth.service.security.url;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author LD
 * @date 2021/5/11 17:17
 */
public class UrlAccessDecisionManager implements AccessDecisionManager {

    /**
     * 权限判断
     *
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        for (ConfigAttribute configAttribute : configAttributes) {

            //访问需要的权限
            String need = configAttribute.getAttribute();

            if (need.equals("ROLE_LOGIN")) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AuthenticationServiceException("未认证");
                } else {
                    return;
                }
            }

            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(need)) {
                    //匹配到相应权限,予以通过
                    return;
                }
            }

            throw new AccessDeniedException("Access is denied");
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
