package com.uanid.myserver.notibot;

import com.uanid.myserver.notibot.notification.NotificationService;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.domain.User;
import com.uanid.myserver.notibot.sender.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Service
public class MessageIntegrationFlowService {

    @Autowired
    private SendService sendService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public void sendNotification(Notification notification) {
        notificationService.saveNotification(notification);

        List<User> users = userService.getSubscribedUsers(notification.getChannel());
        users.forEach(user -> sendService.sendMessage(user, notification));
    }
}
