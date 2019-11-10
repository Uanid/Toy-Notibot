package com.uanid.myserver.notibot.sender;

import com.uanid.myserver.notibot.notification.NotiChannelService;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Component
public class TelegramMessageBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @Autowired
    private NotiChannelService notiChannelService;

    @Autowired
    private UserService userService;

    @Override
    public void onUpdateReceived(Update update) {
        //간단하게 개발
        String text = update.getMessage().getText();
        String resText = null;
        if (text.startsWith("/")) {
            if (text.contains("list")) {
                List<NotiChannel> channels = notiChannelService.getAllNotiChannel();
                StringBuilder sb = new StringBuilder();
                channels.forEach(ch -> sb.append(ch.getName()).append('\n'));
                resText = sb.toString();
            } else if (text.contains("reg")) {
                String channel = text.substring("/reg".length()).trim();
                userService.addSubChannel(update.getMessage().getChatId(), channel);
                resText = "채널 구독 성공";
            } else if (text.contains("help")) {
                resText = "/list - 전체 채널 목록 보기\n" +
                        "/reg <channel> - 채널 구독하기\n" +
                        "/help - 명령어 보기";
            }
        } else {
            resText = "이해할 수 없는 메시지입니다.";
        }

        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId(), resText);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
