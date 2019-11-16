package com.uanid.myserver.notibot.sender.telegram.controller;

import com.uanid.myserver.notibot.notification.NotiChannelService;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.User;
import com.uanid.myserver.notibot.sender.telegram.TelegramController;
import com.uanid.myserver.notibot.sender.telegram.TelegramRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author uanid
 * @since 2019-11-16
 */
@TelegramController
public class ChannelController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotiChannelService notiChannelService;

    @TelegramRequestMapping(value = "/channelsub", alias = "/sub")
    public void subChannel(long chatId, String command, SendMessage message) {
        if (command.length() == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("/channelsub -도움말 보기").append('\n');
            sb.append("/channelsub <채널이름> -채널 구독하기").append('\n');
            sb.append("/channelsub @all -모든 채널 구독하기").append('\n');
            sb.append("/channelsub desub <채널이름> -구독 해제").append('\n');
            sb.append("/channelsub desub @all -모든 채널 구독 해제");
            message.setText(sb.toString());
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            message.setReplyMarkup(keyboardMarkup);
            addFullRowButton(keyboardMarkup.getKeyboard(), "모든 채널 구독하기", "/channelsub @all");
            addFullRowButton(keyboardMarkup.getKeyboard(), "모든 채널 구독 해제", "/channelsub desub @all");
        } else {
            if (command.startsWith("@all")) {
                User user = userService.getUser(chatId);
                user.setSubAllChannel(true);
                userService.saveUser(user);
                message.setText("이제부터 모든 채널에서 발생하는 알림을 받습니다.");
            } else if (command.startsWith("desub @all")) {
                User user = userService.getUser(chatId);
                user.setSubAllChannel(false);
                user.setSubscribeChannel(new ArrayList<>());
                userService.saveUser(user);
                message.setText("이제부터 모든 채널의 알림을 받지 않습니다.");
            } else if (command.startsWith("desub")) {
                String[] args = command.split(" ");
                if (args.length != 2) {
                    message.setText("처리할 수 없는 명령어를 입력했습니다. (인자 수 틀림)");
                    return;
                }

                User user = userService.getUser(chatId);
                if (user.isSubAllChannel()) {
                    message.setText("모든 채널의 알림을 받기로 설정해 특정 채널 구독을 해제할 수 없습니다.");
                    return;
                }

                Optional<NotiChannel> channelOptional = user.getSubscribeChannel().stream()
                        .filter(ch -> ch.getName().equals(args[1]))
                        .findFirst();
                if (channelOptional.isPresent()) {
                    userService.unsubscribeChannel(user, channelOptional.get());
                    message.setText("이제부터 " + channelOptional.get().getName() + " 채널의 알림을 받지 않습니다.");
                } else {
                    message.setText(args[1] + "채널을 구독하지 있지 않습니다.");
                }
            } else {
                String[] args = command.split(" ");
                if (args.length != 1) {
                    message.setText("처리할 수 없는 명령어를 입력했습니다. (인자 수 틀림)");
                    return;
                }

                User user = userService.getUser(chatId);
                if (user.isSubAllChannel()) {
                    message.setText("모든 채널의 알림을 받기로 설정해 특정 채널을 구독할 수 없습니다.");
                    return;
                }

                NotiChannel channel = notiChannelService.getOrCreateChannel(args[0]);
                userService.addSubChannel(user, channel);
                message.setText("이제부터 " + channel.getName() + " 채널을 구독합니다.");
            }
        }
    }

    private void addFullRowButton(List<List<InlineKeyboardButton>> keyboard, String text, String callbackData) {
        List<InlineKeyboardButton> button = new ArrayList<>();
        button.add(new InlineKeyboardButton().setText(text).setCallbackData(callbackData));
        keyboard.add(button);
    }
}
