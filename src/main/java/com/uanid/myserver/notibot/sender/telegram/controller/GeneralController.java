package com.uanid.myserver.notibot.sender.telegram.controller;

import com.uanid.myserver.notibot.R;
import com.uanid.myserver.notibot.notification.UserService;
import com.uanid.myserver.notibot.sender.telegram.TelegramController;
import com.uanid.myserver.notibot.sender.telegram.TelegramRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uanid
 * @since 2019-11-16
 */
@TelegramController
public class GeneralController {

    @Autowired
    private UserService userService;

    @TelegramRequestMapping(name = "/start")
    public void start(long chatId, Update update, TelegramLongPollingBot bot, SendMessage message) {
        User teleUser = update.getMessage().getFrom();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        message.setReplyMarkup(keyboardMarkup);

        List<InlineKeyboardButton> firstRowButton = new ArrayList();
        keyboardMarkup.getKeyboard().add(firstRowButton);

        firstRowButton.add(new InlineKeyboardButton("도움말 보기").setCallbackData("/help"));

        if (userService.getUser(chatId) == null) {
            userService.registerNewUser(chatId, teleUser.getFirstName(), teleUser.getLastName(), null);
            String fullName = teleUser.getLastName() + teleUser.getFirstName();
            message.setText(R.format(R.HELLO_NEW_USER, fullName));
        } else {
            userService.updateUser(chatId, teleUser.getFirstName(), teleUser.getLastName());
            String fullName = teleUser.getLastName() + teleUser.getFirstName();
            message.setText(R.format(R.HELLO_USER, fullName));
        }
    }

    @TelegramRequestMapping("/help")
    public void help(SendMessage message){
        message.setText("명령어는 텍스트 모드로도 이용할 수 있습니다.");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        message.setReplyMarkup(keyboardMarkup);

        List<List<InlineKeyboardButton>> keyboard = keyboardMarkup.getKeyboard();
        addFullRowButton(keyboard, "도움말 보기(/help)", "/help");
        addFullRowButton(keyboard, "채널 구독 (/channelsub)", "/channelsub");
        addFullRowButton(keyboard, "전체 채널 목록(/channellist)", "/channellist");
        addFullRowButton(keyboard, "알림 봇 상태 보기(/status)", "/status");
    }

    private void addFullRowButton(List<List<InlineKeyboardButton>> keyboard, String text, String callbackData){
        List<InlineKeyboardButton> button = new ArrayList<>();
        button.add(new InlineKeyboardButton().setText(text).setCallbackData(callbackData));
        keyboard.add(button);
    }
}
