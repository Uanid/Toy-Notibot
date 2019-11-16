package com.uanid.myserver.notibot.sender.telegram;

import com.uanid.myserver.notibot.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author uanid
 * @since 2019-11-16
 */
@Slf4j
@Component
public class DispatcherController {

    @Autowired
    @Qualifier("telegramCommands")
    private Map<String, TelegramCommand> commandMap;

    @Autowired
    private TelegramMessageHandler telegramMessageHandler;

    @Autowired
    private DispatcherController me;

    @Async("SenderExecutor")
    public void service(Update update) {
        try {
            long chatId = getChatIdFromUpdate(update);
            String command = getCommandFromUpdate(update);
            log.info("A telegram message received {}, {}", chatId, command);

            String cmdPart = command.split(" ")[0];
            String argPart = command.substring(cmdPart.length()).trim();

            TelegramCommand teleCommand = commandMap.get(command.split(" ")[0]);
            if (teleCommand == null) {
                telegramMessageHandler.execute(new SendMessage(chatId, "등록되지 않은 명령어입니다."));
                return;
            }

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            Object[] parameters = getParametersFromCommand(teleCommand, chatId, update, telegramMessageHandler, argPart, message);

            //Spring AOP때문에 Bean으로 된 자신을 호출해 Transactional을 사용
            me.invokeHandler(teleCommand.getHandlerMethod(), teleCommand.getInstance(), parameters);

            telegramMessageHandler.execute(message);
        } catch (Exception e) {
            exceptionHandling(update, e);
        }
    }

    //직접 트랜잭션을 제어해도 되긴 하는데, Spring이 좀 더 깔끔하게 처리해줘서 그냥 이렇게 사용
    @Transactional
    public void invokeHandler(Method method, Object instance, Object[] parameters) throws InvocationTargetException, IllegalAccessException {
        method.invoke(instance, parameters);
    }

    private Object[] getParametersFromCommand(TelegramCommand teleCommand, long chatId, Update update, TelegramLongPollingBot bot, String command, SendMessage message) throws Exception {
        Method method = teleCommand.getHandlerMethod();
        List<Object> parameters = new ArrayList<>();
        for (Class<?> parameterType : method.getParameterTypes()) {
            if (parameterType.equals(Long.TYPE)) {
                parameters.add(chatId);
            } else if (parameterType.isInstance(update)) {
                parameters.add(update);
            } else if (parameterType.isInstance(bot)) {
                parameters.add(bot);
            } else if (parameterType.isInstance(message)) {
                parameters.add(message);
            } else if (parameterType.isInstance(command)) {
                parameters.add(command);
            } else {
                throw new Exception("컨트롤러에 해석할 수 없는 인자가 등록되어 있습니다. " + parameterType.getCanonicalName());
            }
        }
        return parameters.toArray();
    }

    private long getChatIdFromUpdate(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId();
        } else if (update.getCallbackQuery() != null) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new IllegalStateException("처리할 수 있는 메시지가 아닙니다.");
        }
    }

    private String getCommandFromUpdate(Update update) {
        if (update.getMessage() != null) {
            return update.getMessage().getText();
        } else if (update.getCallbackQuery() != null) {
            return update.getCallbackQuery().getData();
        } else {
            throw new IllegalStateException("처리할 수 있는 메시지가 아닙니다.");
        }
    }

    private void exceptionHandling(Update update, Exception e) {
        SendMessage message = new SendMessage();
        message.setChatId(getChatIdFromUpdate(update));
        message.setText(R.format(R.SORRY_FOR_AN_INTERNAL_EXCEPTION, e.getMessage()));
        try {
            telegramMessageHandler.execute(message);
            log.warn("예외 발생, ", e);
        } catch (TelegramApiException ex) {
            log.warn("예외 핸들링 메시지를 보내던 도중 예외 발생", ex);
        }
    }

}
