package com.uanid.myserver.notibot.sender;

import com.uanid.myserver.notibot.R;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Service
public class SenderService {

    @Autowired
    private TelegramMessageBot telegramMessageBot;

    @Autowired
    private UserService userService;

    @Transactional
    @Async(value = "senderExecutor")
    public void sendNotification(Notification notification) {
        log.info("send notification");
        List<User> users = userService.getSubscribedUsers(notification.getChannel());
        users.forEach(user -> this.sendMessage(user, notification));
    }

    public void sendMessage(User user, Notification notification) {
        SendMessage message = buildTelegramMessage(user.getChatId(), notification);
        try {
            telegramMessageBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage buildTelegramMessage(long chatId, Notification noti) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        String text = String.format(R.TELEGRAM_MESSAGE_FORMAT, noti.getChannel().getName(), noti.getBuildNumber(), noti.getWhy(), noti.getWho(), noti.getRegTime().format(DateTimeFormatter.BASIC_ISO_DATE));
        message.setText(text);
        return message;
    }
}
