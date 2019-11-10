package com.uanid.myserver.notibot.notification.repository;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Repository
public interface NotiChannelRepository extends JpaRepository<NotiChannel, Long> {
    NotiChannel findByName(String name);
}
