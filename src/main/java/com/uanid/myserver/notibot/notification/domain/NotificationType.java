package com.uanid.myserver.notibot.notification.domain;

import static com.uanid.myserver.notibot.notification.domain.ReceiverType.*;

/**
 * @author uanid
 * @since 2019-11-14
 */
public enum NotificationType {
    SUCCEEDED, PARTIALLY_SUCCEEDED(AZP), FAILED, STOPPED(AZP), OTHER, CANNOT_UNDERSTAND;

    private ReceiverType[] support;

    NotificationType(ReceiverType... supportReceivers) {
        if (supportReceivers.length == 0) {
            this.support = ReceiverType.values();
        } else {
            this.support = new ReceiverType[supportReceivers.length];
            for (int i = 0; i < supportReceivers.length; i++) {
                support[i] = supportReceivers[i];
            }
        }
    }

    public static NotificationType valueOfByString(String name) {
        for (NotificationType noti : values()) {
            if (noti.name().replace("_", "").equalsIgnoreCase(name)) {
                return noti;
            }
        }
        return null;
    }
}
