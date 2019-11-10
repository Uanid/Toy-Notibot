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
public class NotiChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(length = 100, nullable = false)
    private String name;

    @CreationTimestamp
    private LocalDateTime regTime;

    public NotiChannel() {
    }

    public NotiChannel(String name) {
        this.name = name;
    }
}
