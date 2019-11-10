package com.uanid.myserver.notibot.receiver;

import com.google.common.base.Preconditions;
import com.uanid.myserver.notibot.MessageIntegrationFlowService;
import com.uanid.myserver.notibot.NotibotException;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.NotiChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Service
public class ReceiverService {

    //[Bamboo] MyServer > Network-Infra (DHCP, DNS) > #46 was SUCCESSFUL. Change made by Minuk Song.
    //이렇게 생긴 제목으로 메일이 옮

    private static final String BAMBOO_PREFIX = "[Bamboo]";

    @Autowired
    private NotiChannelService notiChannelService;

    @Autowired
    private MessageIntegrationFlowService messageIntegrationFlowService;

    public void postNotification(Notification notification) {
        messageIntegrationFlowService.sendNotification(notification);
    }

    public Notification buildNotificationFromSubject(String mailSubject) throws NotibotException {
        Preconditions.checkArgument(mailSubject != null, "Subject of mail can not be null");
        Preconditions.checkArgument(!mailSubject.isEmpty(), "Subject of mail can not be empty String");
        Preconditions.checkArgument(mailSubject.startsWith(BAMBOO_PREFIX), "Subject of mail should start with %d", BAMBOO_PREFIX);

        try {
            Notification noti = new Notification();
            noti.setFullMessage(mailSubject);

            String str = mailSubject.substring(BAMBOO_PREFIX.length());
            String channelName = str.substring(0, str.lastIndexOf('>')).trim();
            noti.setChannel(notiChannelService.getOrCreateChannel(channelName));

            str = str.substring(str.lastIndexOf('#'));
            String rawBuildNumber = str.substring(0, str.indexOf(' ')).trim();
            int buildNumber = Integer.parseInt(rawBuildNumber.substring(1));
            noti.setBuildNumber(buildNumber);

            String[] messages = str.split("\\.");
            for (String message : messages) {
                message = message.trim();
                if (message.contains("SUCCESSFUL")) {
                    noti.setWhy("Build Success");
                } else if (message.contains("FAIL")) {
                    noti.setWhy("Build Fail");
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
