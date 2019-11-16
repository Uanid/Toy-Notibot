package com.uanid.myserver.notibot.notification.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    private long seq;

    @Column(nullable = false)
    private long chatId;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<NotiChannel> subscribeChannel;
}
