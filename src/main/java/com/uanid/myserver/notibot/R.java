package com.uanid.myserver.notibot;

/**
 * @author uanid
 * @since 2019-11-10
 */
public final class R {
    public static String UNKNOWN_CHANNEL = "Unknown > Channel";
    public static String HELLO_NEW_USER = "안녕하세요 %s님!\n빌드 알림 봇과 처음 만나신걸 환영합니다.\n빌드 알림 봇은 /help 명령어와 버튼으로 사용할 수 있습니다.";
    public static String HELLO_USER = "또 오셨군요 %s님!\n빌드 알림 봇과 다시 만나게 된 것을 환영합니다.\n빌드 알림 봇은 /help 명령어와 버튼으로 사용할 수 있습니다.";
    public static String TELEGRAM_MESSAGE_FORMAT = "빌드 알림\n 채널: %s\n 빌드번호: %d\n Why: %s\n Who: %s\n 시간: %s\nhttp://dev.azure.com/uanid";
    public static String SORRY_FOR_AN_INTERNAL_EXCEPTION = "죄송합니다. 요청을 처리하는데 오류가 발생했습니다.\n에러메시지: %s";

    public static String format(String format, Object... args){
        return String.format(format, args);
    }
}
