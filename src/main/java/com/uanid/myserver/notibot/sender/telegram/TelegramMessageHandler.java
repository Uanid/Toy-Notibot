package com.uanid.myserver.notibot.sender.telegram;

import com.uanid.myserver.notibot.notification.NotiChannelService;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Component
public class TelegramMessageHandler extends TelegramLongPollingBot {

    @Value("${sender.telegram.username}")
    private String username;

    @Value("${sender.telegram.token}")
    private String token;

    @Autowired
    private DispatcherController dispatcherController;

    @Override
    public void onUpdateReceived(Update update) {
        dispatcherController.service(update);
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
