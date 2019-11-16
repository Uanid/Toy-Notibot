package com.uanid.myserver.notibot.notification.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private long chatId;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @CreationTimestamp
    private LocalDateTime regTime;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<NotiChannel> subscribeChannel;
}
