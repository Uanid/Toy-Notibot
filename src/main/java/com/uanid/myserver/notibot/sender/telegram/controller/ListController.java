package com.uanid.myserver.notibot.sender.telegram.controller;

import com.uanid.myserver.notibot.notification.NotiChannelService;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.User;
import com.uanid.myserver.notibot.sender.telegram.TelegramController;
import com.uanid.myserver.notibot.sender.telegram.TelegramRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

/**
 * @author uanid
 * @since 2019-11-16
 */
@TelegramController
public class ListController {

    @Autowired
    private NotiChannelService notiChannelService;

    @Autowired
    private UserService userService;

    @TelegramRequestMapping(value = "/channellist", alias = "/list")
    public void list(long chatId, SendMessage message) {
        List<NotiChannel> channels = notiChannelService.getAllNotiChannel();
        User user = userService.getUser(chatId);

        StringBuilder sb = new StringBuilder();
        sb.append("전체 채널 목록 (S는 구독완료)\n");

        int count = 1;
        for (NotiChannel channel : channels) {
            boolean isSubscribed = user.getSubscribeChannel().contains(channels);
            String subStr = (isSubscribed || user.isSubAllChannel()) ? "(S)" : "";
            sb.append(String.format("%d. %s%s\n", count, subStr, channel.getName()));
        }
        message.setText(sb.toString());
    }
}
