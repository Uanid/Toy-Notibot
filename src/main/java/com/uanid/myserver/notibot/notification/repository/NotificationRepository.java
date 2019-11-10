package com.uanid.myserver.notibot.notification.repository;

import com.uanid.myserver.notibot.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
