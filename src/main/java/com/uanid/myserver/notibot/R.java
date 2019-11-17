package com.uanid.myserver.notibot;

import org.apache.logging.log4j.core.lookup.StrSubstitutor;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

/**
 * @author uanid
 * @since 2019-11-10
 */
public final class R {

    private static ZoneId zone = ZoneId.of("Asia/Tokyo");
    private static final String RFC_1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final String RFC_1036_PATTERN = "EEEE, dd-MMM-yy HH:mm:ss z";
    private static final String ASCTIME_PATTERN = "EEE MMM d HH:mm:ss yyyyy";
    private static final String MY_KOREAN_PATTERN = "yyyy-MM-dd(EEE) HH:mm:ss";

    public static String HELLO_NEW_USER = "안녕하세요 %s님!\n" +
            "빌드 알림 봇과 처음 만나신걸 환영합니다.\n" +
            "빌드 알림 봇은 /help 명령어와 버튼으로 사용할 수 있습니다.";
    public static String HELLO_USER = "또 오셨군요 %s님!\n" +
            "빌드 알림 봇과 다시 만나게 된 것을 환영합니다.\n" +
            "빌드 알림 봇은 /help 명령어와 버튼으로 사용할 수 있습니다.";
    public static String TELEGRAM_MESSAGE_FORMAT = "%{title}\n" +
            "채널: %{channel}\n" +
            "빌드 번호: %{buildNumber}\n" +
            "Reason: %{reason}\n" +
            "Requested for: %{requestedFor}\n" +
            "Finished: %{regTime}\n" +
            "http://dev.azure.com/uanid";

    public static String SORRY_FOR_AN_INTERNAL_EXCEPTION = "죄송합니다. 요청을 처리하는데 오류가 발생했습니다.\n에러메시지: %s";
    public static DateTimeFormatter RFC_1123_FORMAT = DateTimeFormatter.ofPattern(RFC_1123_PATTERN, Locale.KOREA).withZone(zone);
    public static DateTimeFormatter RFC_1036_FORMAT = DateTimeFormatter.ofPattern(RFC_1036_PATTERN, Locale.KOREA).withZone(zone);
    public static DateTimeFormatter ASCTIME_FORMAT = DateTimeFormatter.ofPattern(ASCTIME_PATTERN, Locale.KOREA).withZone(zone);
    public static DateTimeFormatter MY_KOREAN_FORMAT = DateTimeFormatter.ofPattern(MY_KOREAN_PATTERN, Locale.KOREA).withZone(zone);


    public static String format(String format, Object... args) {
        return String.format(format, args);
    }

    public static String placeholder(String format, Map<String, String> values) {
        StrSubstitutor sub = new StrSubstitutor(values, "%{", "}");
        return sub.replace(format);
    }
}
