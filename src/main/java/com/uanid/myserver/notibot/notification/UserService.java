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
import java.util.Set;
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

    public User getUser(long chatId) {
        return userRepository.findByChatId(chatId);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void unsubscribeChannel(User user, NotiChannel notiChannel) {
        userRepository.deleteSubscribeChannel(user.getUserId(), notiChannel.getChannelId());
    }

    public void updateUser(long chatId, String firstName, String lastName) {
        User user = getUser(chatId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.save(user);
    }

    public User registerNewUser(long chatId, String firstName, String lastName, List<String> subscribeChannel) {
        User user = new User();
        user.setChatId(chatId);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (subscribeChannel != null) {
            List<NotiChannel> channels = subscribeChannel.stream().map(notiChannelService::getOrCreateChannel).collect(Collectors.toList());
            user.setSubscribeChannel(channels);
        }

        return userRepository.save(user);
    }

    public void addSubChannel(User user, NotiChannel notiChannel) {
        userRepository.addSubscribeChannel(user.getUserId(), notiChannel.getChannelId());
    }

    public List<User> getSubscribedUsers(NotiChannel notiChannel) {
        List<User> subUsers = userRepository.findAllBySubscribeChannelContaining(notiChannel);
        List<User> allSubUsers = userRepository.findAllByIsSubAllChannelEquals(true);

        List<User> mergedUsers = new ArrayList<>(subUsers);
        for (User user : allSubUsers) {
            if (!mergedUsers.contains(user)) {
                mergedUsers.add(user);
            }
        }
        return mergedUsers;
    }
}
