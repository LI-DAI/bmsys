package com.ld.bmsys.auth.service.security.anon;

import java.lang.annotation.*;

/**
 * @Author LD
 * @Date 2021/4/23 16:41
 * <p>
 * 匿名访问注解
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {


}
