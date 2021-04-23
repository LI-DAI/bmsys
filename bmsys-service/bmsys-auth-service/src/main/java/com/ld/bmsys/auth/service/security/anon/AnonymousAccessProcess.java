package com.ld.bmsys.auth.service.security.anon;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.google.common.collect.ImmutableSet;
import com.ld.bmsys.auth.service.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ld.bmsys.common.constant.CommonConstant.ANON_CACHE_KEY;

/**
 * @Author LD
 * @Date 2021/4/23 16:41
 */
@Component
public class AnonymousAccessProcess {

    private final ApplicationContext applicationContext;
    private final SecurityProperties properties;

    public static Cache<String, Set<String>> anonymousCache = CacheUtil.newFIFOCache(3);

    public AnonymousAccessProcess(ApplicationContext applicationContext, SecurityProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {

        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();

        Set<String> anonymousUri = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod method = entry.getValue();
            AnonymousAccess annotation = method.getMethodAnnotation(AnonymousAccess.class);
            if (annotation != null) {
                RequestMappingInfo mappingInfo = entry.getKey();
                Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
                anonymousUri.addAll(patterns);
            }
        }
        Set<String> propertiesUri = properties.getAnonUri();
        Set<String> anon = ImmutableSet.of(anonymousUri, propertiesUri).stream().flatMap(Collection::stream).collect(Collectors.toSet());
        anonymousCache.put(ANON_CACHE_KEY, anon);
    }
}
