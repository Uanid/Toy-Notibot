package com.uanid.myserver.notibot.receiver.imap;

import com.google.common.base.Preconditions;
import com.uanid.myserver.notibot.NotibotException;
import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.domain.NotificationType;
import com.uanid.myserver.notibot.notification.domain.ReceiverType;
import com.uanid.myserver.notibot.receiver.ReceiverService;
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

    private static final String BAMBOO_PREFIX = "[Bamboo]";

    @Autowired
    private ReceiverService receiverService;

    @Override
    @Transactional
    public void handleMessage(Message<?> rawMessage) throws MessagingException {
        MimeMessage message = (MimeMessage) rawMessage.getPayload();
        try {
            Notification notification = buildNotificationFromSubject(message.getSubject());
            receiverService.postNotification(notification);

        } catch (javax.mail.MessagingException e) {
            Notification notification = new Notification();
            notification.setFullMessage(e.getMessage());
            log.warn("메일 에러 발생 " + notification.toString());

        } catch (NotibotException e) {
            Notification noti = new Notification();
            try {
                noti.setFullMessage(message.getSubject());
                noti.setNotificationType(NotificationType.CANNOT_UNDERSTAND);
                noti.setReceiverType(ReceiverType.BAMBOO);
                receiverService.postNotification(noti);
            } catch (javax.mail.MessagingException ex) {
                ex.printStackTrace();
            }
            log.warn("노티 빌드중 에러 발생 " + noti.toString());
        } catch (Exception e) {
            log.warn("모르는 에러 발생 ", e);
        }
    }

    //[Bamboo] MyServer > Network-Infra (DHCP, DNS) > #46 was SUCCESSFUL. Change made by Minuk Song.
    //이렇게 생긴 제목으로 메일이 옮
    private Notification buildNotificationFromSubject(String mailSubject) throws NotibotException {
        Preconditions.checkArgument(mailSubject != null, "Subject of mail can not be null");
        Preconditions.checkArgument(!mailSubject.isEmpty(), "Subject of mail can not be empty String");
        Preconditions.checkArgument(mailSubject.startsWith(BAMBOO_PREFIX), "Subject of mail should start with %d", BAMBOO_PREFIX);

        try {
            Notification noti = new Notification();
            noti.setFullMessage(mailSubject);

            String str = mailSubject.substring(BAMBOO_PREFIX.length());
            String channelName = str.substring(0, str.lastIndexOf('>')).trim();
            noti.setChannel(new NotiChannel(channelName));

            str = str.substring(str.lastIndexOf('#'));
            String rawBuildNumber = str.substring(0, str.indexOf(' ')).trim();
            int buildNumber = Integer.parseInt(rawBuildNumber.substring(1));
            noti.setBuildNumber(buildNumber);
            noti.setReceiverType(ReceiverType.BAMBOO);

            String[] messages = str.split("\\.");
            for (String message : messages) {
                message = message.trim();
                if (message.contains("SUCCESSFUL")) {
                    noti.setWhy("Build Success");
                    noti.setNotificationType(NotificationType.SUCCEEDED);
                } else if (message.contains("FAIL")) {
                    noti.setWhy("Build Fail");
                    noti.setNotificationType(NotificationType.FAILED);
                } else if (message.contains("Change made by")) {
                    String who = message.substring("Change made by".length() + 1).trim();
                    noti.setWho(who);
                }
            }
            return noti;
        } catch (Exception e) {
            throw new NotibotException("A nested exception occurred when create notification", e);
        }
    }
}
