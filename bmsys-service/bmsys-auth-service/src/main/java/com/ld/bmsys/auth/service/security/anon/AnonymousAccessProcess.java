package com.ld.bmsys.auth.service.security.anon;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.google.common.collect.ImmutableSet;
import com.ld.bmsys.auth.service.security.SecurityProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ld.bmsys.common.constant.CommonConstant.ANON_CACHE_KEY;

/**
 * @Author LD
 * @Date 2021/4/23 16:41
 */
@Component
public class AnonymousAccessProcess {

    private final SecurityProperties properties;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public static Cache<String, Set<String>> anonymousCache = CacheUtil.newFIFOCache(3);

    public AnonymousAccessProcess(RequestMappingHandlerMapping requestMappingHandlerMapping, SecurityProperties properties) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {

        Set<String> anonymousUri = new HashSet<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod method = entry.getValue();
            AnonymousAccess annotation = method.getMethodAnnotation(AnonymousAccess.class);
            if (annotation != null) {
                Secured secured = method.getMethodAnnotation(Secured.class);
                PreAuthorize preAuthorize = method.getMethodAnnotation(PreAuthorize.class);
                PostAuthorize postAuthorize = method.getMethodAnnotation(PostAuthorize.class);
                //已标注权限注解的方法,默认不可以匿名访问
                boolean anyMatch = Stream.of(secured, preAuthorize, postAuthorize).anyMatch(Objects::nonNull);
                if (anyMatch) continue;
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
