package run.ut.app.service;



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
    void sendCode(String tel, String code);

    /**
     * check verification code
     * @param tel phone number
     * @param code Verification code
     */
    void checkCode(String tel, String code);
}
