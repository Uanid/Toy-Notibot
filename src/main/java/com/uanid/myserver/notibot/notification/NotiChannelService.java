package com.uanid.myserver.notibot.notification;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.repository.NotiChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Service
public class NotiChannelService {
    @Autowired
    private NotiChannelRepository notiChannelRepository;

    public NotiChannel getOrCreateChannel(String channelName) {
        NotiChannel notiChannel = notiChannelRepository.findByName(channelName);
        if (notiChannel == null) {
            notiChannel = notiChannelRepository.save(new NotiChannel(channelName));
        }
        return notiChannel;
    }

    public List<NotiChannel> getAllNotiChannel() {
        return notiChannelRepository.findAll();
    }
}
