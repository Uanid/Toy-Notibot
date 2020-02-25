package com.uanid.myserver.notibot.receiver.http;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.domain.NotificationType;
import com.uanid.myserver.notibot.notification.domain.ReceiverType;
import com.uanid.myserver.notibot.receiver.ReceiverService;
import com.uanid.myserver.notibot.receiver.http.model.AzpWebhook;
import com.uanid.myserver.notibot.receiver.http.model.Definition;
import com.uanid.myserver.notibot.receiver.http.model.Request;
import com.uanid.myserver.notibot.receiver.http.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uanid
 * @since 2019-11-13
 */
@Slf4j
@RestController
@RequestMapping("/")
public class HttpEndPoint {

    @Autowired
    private ReceiverService receiverService;

    @RequestMapping(value = {"", "/noti/azp"})
    public String listen(HttpServletRequest request, @RequestBody AzpWebhook azpWebhook) {
        Notification noti = new Notification();
        noti.setFullMessage(azpWebhook.getDetailedMessage().getText());

        Resource azpResource = azpWebhook.getResource();
        Definition azpDefinition = azpResource.getDefinition();
        NotiChannel notiChannel = new NotiChannel(azpDefinition.getName());
        noti.setChannel(notiChannel);

        noti.setReceiverType(ReceiverType.AZP);
        noti.setNotificationType(NotificationType.valueOfByString(azpResource.getStatus()));

        noti.setWhy(azpResource.getReason());
        noti.setBuildNumber((int) ((long) azpResource.getId()));

        List<Request> requests = azpResource.getRequests();
        List<String> requestStr = requests.stream()
                .map(Request::getRequestedFor)
                .map(req -> req.getDisplayName() + "/" + req.getUniqueName())
                .collect(Collectors.toList());
        String who = StringUtils.collectionToCommaDelimitedString(requestStr);
        if (requests.size() != 1) {
            who = String.format("[%s]", who);
        }
        noti.setWho(who);

        noti.setCommittedWho(azpResource.getLastChangedBy().getDisplayName() + "/" + azpResource.getLastChangedBy().getUniqueName());

        receiverService.postNotification(noti);
        return "Cheer up!";
    }

    @RequestMapping("/noti/loggedin")
    public String message(@RequestParam String message,
                          @RequestParam String who,
                          @RequestParam String channel) {
        Notification noti = new Notification();
        noti.setChannel(new NotiChannel(channel));
        noti.setNotificationType(NotificationType.LOGGED_IN);
        noti.setReceiverType(ReceiverType.MESSAGE);
        noti.setWho(who);
        noti.setCommittedWho(who);
        noti.setWhy("Someone Logged In");
        noti.setBuildNumber(0);
        noti.setFullMessage(message);
        receiverService.postNotification(noti);
        return "Cheer up!";
    }


}
