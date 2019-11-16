package com.uanid.myserver.notibot.sender.telegram;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * @author uanid
 * @since 2019-11-16
 */
@Getter
@Setter
@Builder
public class TelegramCommand {
    private String name;

    private Class<?> controller;

    private Method handlerMethod;

    private Object instance;

}
