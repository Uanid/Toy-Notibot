package com.uanid.myserver.notibot.notification.repository;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<User> findAllByIsSubAllChannelEquals(boolean isSubAllChannel);

    @Query(value = "delete from user_subscribe_channel where user_user_id = ?1 and subscribe_channel_channel_id = ?2", nativeQuery = true)
    void deleteSubscribeChannel(long userId, long channelId);

    @Query(value = "insert into user_subscribe_channel (user_user_id, subscribe_channel_channel_id) values (?1, ?2)", nativeQuery = true)
    void addSubscribeChannel(long userId, long channelId);
}
