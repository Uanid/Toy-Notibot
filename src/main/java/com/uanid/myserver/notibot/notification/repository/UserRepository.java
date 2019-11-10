package com.uanid.myserver.notibot.notification.repository;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByChatId(long chatId);
    List<User> findAllBySubscribeChannelContaining(NotiChannel notiChannel);
}
