package com.uanid.myserver.notibot.sender;

import com.uanid.myserver.notibot.R;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.domain.User;
import com.uanid.myserver.notibot.sender.telegram.TelegramMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Service
public class SenderService {

    @Autowired
    private TelegramMessageHandler telegramMessageHandler;

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
            telegramMessageHandler.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage buildTelegramMessage(long chatId, Notification n) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        Map<String, String> values = new HashMap<>();
        values.put("title", n.getFullMessage());
        values.put("channel", n.getChannel().getName());
        values.put("buildNumber", String.valueOf(n.getBuildNumber()));
        values.put("reason", n.getWhy());
        values.put("requestedFor", n.getCommittedWho());
        values.put("regTime", n.getRegTime().format(R.MY_KOREAN_FORMAT));

        message.setText(R.placeholder(R.TELEGRAM_MESSAGE_FORMAT, values));
        return message;
    }
}
