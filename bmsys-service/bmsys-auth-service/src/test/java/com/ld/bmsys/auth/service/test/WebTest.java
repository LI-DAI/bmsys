package com.ld.bmsys.auth.service.test;

import com.ld.bmsys.auth.service.security.anon.AnonymousAccess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * @author LD
 * @date 2021/5/14 11:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebTest {

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private ListableBeanFactory listableBeanFactory;

    @Test
    public void test() {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        ServletContext context = requestAttributes.getRequest().getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context);

        Map<String, Object> controllerBeans = webApplicationContext.getBeansWithAnnotation(RestController.class);

        System.out.println(controllerBeans);


        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();

            PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();

            ConsumesRequestCondition consumesCondition = requestMappingInfo.getConsumesCondition();
            RequestCondition<?> customCondition = requestMappingInfo.getCustomCondition();
            HeadersRequestCondition headersCondition = requestMappingInfo.getHeadersCondition();
            RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
            ParamsRequestCondition paramsCondition = requestMappingInfo.getParamsCondition();
            ProducesRequestCondition producesCondition = requestMappingInfo.getProducesCondition();


            HandlerMethod method = entry.getValue();

            AnonymousAccess methodAnnotation = method.getMethodAnnotation(AnonymousAccess.class);

            System.out.println();


        }

        Map<String, Object> beansWithAnnotation = listableBeanFactory.getBeansWithAnnotation(AnonymousAccess.class);

        System.out.println(beansWithAnnotation);
    }
}
