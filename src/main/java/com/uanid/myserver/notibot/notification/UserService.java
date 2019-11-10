package com.uanid.myserver.notibot.notification;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.User;
import com.uanid.myserver.notibot.notification.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotiChannelService notiChannelService;

    public User getOrCreateUser(long chatId) {
        User user = userRepository.findByChatId(chatId);
        if (user == null) {
            user = registerNewUser(chatId, null);
            log.warn("신규 유저 등록 " + user.toString());
        }
        return user;
    }

    public User registerNewUser(long chatId, List<String> subscribeChannel) {
        User user = new User();
        user.setChatId(chatId);

        if (subscribeChannel != null) {
            List<NotiChannel> channels = subscribeChannel.stream().map(notiChannelService::getOrCreateChannel).collect(Collectors.toList());
            user.setSubscribeChannel(channels);
        }

        return userRepository.save(user);
    }

    @Transactional
    public void addSubChannel(long chatId, String channelName) {
        NotiChannel channel = notiChannelService.getOrCreateChannel(channelName);
        User user = getOrCreateUser(chatId);
        if (user.getSubscribeChannel() == null) {
            user.setSubscribeChannel(new ArrayList<>());
        }
        user.getSubscribeChannel().add(channel);
        log.warn("채널 구독 " + user.toString() + "/" + channel.toString());
        userRepository.save(user);
    }

    public List<User> getSubscribedUsers(NotiChannel notiChannel) {
        return userRepository.findAllBySubscribeChannelContaining(notiChannel);
    }
}
