package com.uanid.myserver.notibot.receiver.imap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.messaging.MessageChannel;

import java.util.Properties;

/**
 * @author uanid
 * @since 2019-11-10
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "receiver.bamboo", name = "enable", havingValue = "true", matchIfMissing = false)
public class MailConfig {

    @Value("${receiver.bamboo.imap-url}")
    private String mailReceiveUrl;

    @Value("${receiver.bamboo.scan-interval:10}")
    private int scanInterval;

    @Autowired
    private MailReceiveHandler mailReceiveHandler;

    @Bean("customAdapter")
    public ImapIdleChannelAdapter customAdapter() {
        log.warn("메일 확인 리시버 활성화");
        ImapIdleChannelAdapter imapChannelAdapter = new ImapIdleChannelAdapter(imapReceiver());
        imapChannelAdapter.setAutoStartup(true);
        imapChannelAdapter.setShouldReconnectAutomatically(true);
        imapChannelAdapter.setOutputChannel(receiveChannel());
        return imapChannelAdapter;
    }

    @Bean
    public ImapMailReceiver imapReceiver() {
        ImapMailReceiver receiver = new ImapMailReceiver(mailReceiveUrl);
        Properties properties = new Properties();
        properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.store.protocol", "imaps");
        properties.setProperty("mail.debug", "false");
        properties.setProperty("mail.imaps.auth.plain.disable", "true");
        properties.setProperty("mail.imaps.auth.xoauth2.disable", "true");
        receiver.setJavaMailProperties(properties);
        receiver.setShouldMarkMessagesAsRead(true);
        receiver.setShouldDeleteMessages(false);
        receiver.setCancelIdleInterval(scanInterval);
        return receiver;
    }

    @Bean
    public MessageChannel receiveChannel() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.subscribe(mailReceiveHandler);
        return directChannel;
    }
}
