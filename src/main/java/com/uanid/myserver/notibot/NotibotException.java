package com.uanid.myserver.notibot;

/**
 * @author uanid
 * @since 2019-11-10
 */
public class NotibotException extends Exception {
    public NotibotException() {
    }

    public NotibotException(String message) {
        super(message);
    }

    public NotibotException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotibotException(Throwable cause) {
        super(cause);
    }

    public NotibotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
