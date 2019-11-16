package com.uanid.myserver.notibot.sender.telegram;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * @author uanid
 * @since 2019-11-16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TelegramController {

}
