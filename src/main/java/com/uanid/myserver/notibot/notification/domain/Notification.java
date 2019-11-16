package com.uanid.myserver.notibot.notification.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Entity
@Getter
@Setter
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notiId;

    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private NotiChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char(32)")
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "noti_source", columnDefinition = "char(32)")
    private ReceiverType receiverType;

    @Column(length = 512)
    private String who;

    @Column(length = 512)
    private String committedWho;

    @Column(length = 100)
    private String why;

    @Column(nullable = false)
    private int buildNumber;

    @Column(length = 255, nullable = false)
    private String fullMessage;

    @CreationTimestamp
    private LocalDateTime regTime;

    public void setChannel(NotiChannel notiChannel) {
        this.channel = notiChannel;
        notiChannel.getNotifications().add(this);
    }
}
