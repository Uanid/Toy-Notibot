package com.uanid.myserver.notibot.notification;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotiChannelService notiChannelService;

    @Transactional
    public Notification saveNotification(Notification notification) {
        NotiChannel notiChannel = notiChannelService.getOrCreateChannel(notification.getChannel().getName());
        notification.setChannel(notiChannel);
        return notificationRepository.save(notification);
    }
}
