package com.ld.bmsys.auth.service.security.anon;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;
import com.ld.bmsys.auth.service.security.SecurityProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ld.bmsys.common.constant.CommonConstant.ANON_CACHE_KEY;

/**
 * @author LD
 * @date 2021/4/23 16:41
 */
public class AnonymousAccessProcess {

    private final SecurityProperties properties;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public static Cache<String, Set<String>> anonymousCache;

    public static void loadAnonymousAccessProcess(RequestMappingHandlerMapping requestMappingHandlerMapping, SecurityProperties properties) {
        new AnonymousAccessProcess(requestMappingHandlerMapping, properties);
    }

    private AnonymousAccessProcess(RequestMappingHandlerMapping requestMappingHandlerMapping, SecurityProperties properties) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.properties = properties;
        init();
    }

    public void init() {
        Set<String> anonymousUri = new HashSet<>();
        //从处理器映射器获取 handler methods
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod method = entry.getValue();
            AnonymousAccess annotation = method.getMethodAnnotation(AnonymousAccess.class);
            if (annotation != null) {
                //已标注权限注解的方法,默认不可以匿名访问
                boolean anyMatch = method.hasMethodAnnotation(Secured.class)
                        || method.hasMethodAnnotation(PreAuthorize.class)
                        || method.hasMethodAnnotation(PostAuthorize.class);
                if (anyMatch) continue;
                RequestMappingInfo mappingInfo = entry.getKey();
                Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
                anonymousUri.addAll(patterns);
            }
        }

        Set<String> propertiesUri = properties.getAnonUri();
        Set<String> anon = ImmutableSet.of(anonymousUri, propertiesUri).stream().flatMap(Collection::stream).collect(Collectors.toSet());

        anonymousCache = CacheBuilder.newBuilder().maximumSize(3).build();

        anonymousCache.put(ANON_CACHE_KEY, anon);
    }
}
