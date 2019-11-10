package com.uanid.myserver.notibot.notification;

import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
