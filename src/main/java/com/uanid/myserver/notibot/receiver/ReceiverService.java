package com.uanid.myserver.notibot.receiver;

import com.uanid.myserver.notibot.notification.NotificationService;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.sender.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Service
public class ReceiverService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SenderService senderService;

    public void postNotification(Notification notification) {
        log.info("Notification posted {}", notification.toString());
        notificationService.saveNotification(notification);

        senderService.sendNotification(notification);
    }


}
