package com.uanid.myserver.notibot.receiver.azp;

import com.uanid.myserver.notibot.notification.domain.NotiChannel;
import com.uanid.myserver.notibot.notification.domain.Notification;
import com.uanid.myserver.notibot.notification.domain.NotificationType;
import com.uanid.myserver.notibot.notification.domain.ReceiverType;
import com.uanid.myserver.notibot.receiver.ReceiverService;
import com.uanid.myserver.notibot.receiver.azp.model.AzpWebhook;
import com.uanid.myserver.notibot.receiver.azp.model.Definition;
import com.uanid.myserver.notibot.receiver.azp.model.Request;
import com.uanid.myserver.notibot.receiver.azp.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author uanid
 * @since 2019-11-13
 */
@Slf4j
@Controller
public class AzpWebhookHandler {

    @Autowired
    private ReceiverService receiverService;

    @RequestMapping("/**")
    @ResponseBody
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

        StringBuilder who = new StringBuilder();
        List<Request> requests = azpResource.getRequests();
        List<String> requestStr = requests.stream()
                .map(Request::getRequestedFor)
                .map(req -> req.getDisplayName() + "/" + req.getUniqueName())
                .collect(Collectors.toList());
        String flatStr = StringUtils.collectionToCommaDelimitedString(requestStr);
        if (requests.size() != 1) {
            flatStr = String.format("[%s]", flatStr);
        }
        who.append(flatStr);
        noti.setWho(who.toString());

        noti.setCommittedWho(azpResource.getLastChangedBy().getDisplayName() + "/" + azpResource.getLastChangedBy().getUniqueName());

        receiverService.postNotification(noti);
        return "Cheer up!";
    }


}
