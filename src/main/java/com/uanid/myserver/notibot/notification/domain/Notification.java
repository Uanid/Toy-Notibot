package com.uanid.myserver.notibot.notification.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private long seq;

    @ManyToOne(fetch = FetchType.EAGER)
    private NotiChannel channel;

    @Column(length = 100)
    private String who;

    @Column(length = 100)
    private String why;

    @Column(nullable = false)
    private int buildNumber;

    @Column(length = 255, nullable = false)
    private String fullMessage;

    @CreationTimestamp
    private LocalDateTime regTime;
}
