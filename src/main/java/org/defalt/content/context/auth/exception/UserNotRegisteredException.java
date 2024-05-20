package org.defalt.content.context.auth.exception;


import java.util.function.Supplier;

public class UserNotRegisteredException extends RuntimeException {

    public static Supplier<UserNotRegisteredException> supply(String message) {
        return () -> new UserNotRegisteredException(message);
    }

    public UserNotRegisteredException() {
    }

    public UserNotRegisteredException(String message) {
        super(message);
    }

    public UserNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotRegisteredException(Throwable cause) {
        super(cause);
    }

    public UserNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
