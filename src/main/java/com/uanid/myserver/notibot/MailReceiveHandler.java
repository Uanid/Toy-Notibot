package com.uanid.myserver.notibot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Component
public class MailReceiveHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> rawMessage) throws MessagingException {
        log.warn(rawMessage.getHeaders().toString());

        MimeMessage message = (MimeMessage) rawMessage.getPayload();

    }
}
