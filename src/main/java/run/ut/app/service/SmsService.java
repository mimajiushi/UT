package run.ut.app.service;


import org.springframework.lang.NonNull;

/**
 * SMS service
 *
 * @author wenjie
 */
public interface SmsService {
    /**
     * SMS messages
     * @param tel phone number
     * @param code Verification code
     */
    void sendCode(@NonNull String tel, @NonNull String code);

    /**
     * check verification code
     * @param tel phone number
     * @param code Verification code
     */
    void checkCode(@NonNull String tel, @NonNull String code);
}
