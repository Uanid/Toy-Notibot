package com.uanid.myserver.notibot.sender.telegram.controller;

import com.uanid.myserver.notibot.sender.telegram.TelegramController;
import com.uanid.myserver.notibot.sender.telegram.TelegramRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author uanid
 * @since 2019-11-16
 */
@TelegramController
public class StatusController {

    @Autowired
    private Environment environment;

    @TelegramRequestMapping("/status")
    public void status(SendMessage message) {
        String telegram = environment.getProperty("telegrambots.enabled");
        String bamboo = environment.getProperty("receiver.bamboo.enable");
        String azp = environment.getProperty("spring.main.web-application-type");
        String database = environment.getProperty("spring.datasource.url");
        StringBuilder sb = new StringBuilder();
        sb.append("Receiver\n");
        sb.append("1.Bamboo: " + bamboo + "\n");
        sb.append("2.Azure Pipeline: " + azp + "\n");
        sb.append("Sender\n");
        sb.append("1.Telegram: " + telegram + "\n");
        sb.append("2.KakaoTalk: On Developing\n");
//        sb.append("Database\n");
//        sb.append("1.Database: " + database);
        message.setText(sb.toString());
    }
}
