package com.uanid.myserver.notibot.notification.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Entity
@Getter
@Setter
@ToString(exclude = "notifications")
public class NotiChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long channelId;

    @Column(length = 100, nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime regTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "channel", cascade = CascadeType.PERSIST)
    private List<Notification> notifications = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Map<User, Notification> notifiedStatus = new HashMap<>();

    public NotiChannel() {
    }

    public NotiChannel(String name) {
        this.name = name;
    }
}
