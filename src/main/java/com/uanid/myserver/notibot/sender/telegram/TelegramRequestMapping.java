package com.uanid.myserver.notibot.sender.telegram;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author uanid
 * @since 2019-11-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface TelegramRequestMapping {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String[] alias() default {};
}
