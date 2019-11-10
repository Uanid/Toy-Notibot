package com.uanid.myserver.notibot.receiver;

import com.uanid.myserver.notibot.MessageIntegrationFlowService;
import com.uanid.myserver.notibot.NotibotException;
import com.uanid.myserver.notibot.R;
import com.uanid.myserver.notibot.notification.domain.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Component
public class MailReceiveHandler implements MessageHandler {

    @Autowired
    private ReceiverService receiverService;

    @Override
    @Transactional
    public void handleMessage(Message<?> rawMessage) throws MessagingException {
        log.warn(rawMessage.getHeaders().toString());

        MimeMessage message = (MimeMessage) rawMessage.getPayload();
        try {
            Notification notification = receiverService.buildNotificationFromSubject(message.getSubject());
            log.warn(notification.toString());
            receiverService.postNotification(notification);
        } catch (javax.mail.MessagingException e) {
            Notification notification = new Notification();
            notification.setFullMessage(e.getMessage());
            log.warn("메일 에러 발생 " + notification.toString());
        } catch (NotibotException e) {
            Notification notification = new Notification();
            try {
                notification.setFullMessage(message.getSubject());
            } catch (javax.mail.MessagingException ex) {
                ex.printStackTrace();
            }
            log.warn("노티 빌드중 에러 발생 " + notification.toString());
        } catch (Exception e) {
            log.warn("모르는 에러 발생 ", e);
        }
    }
}
