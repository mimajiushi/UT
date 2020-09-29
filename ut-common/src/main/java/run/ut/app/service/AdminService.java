package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.AdminLoginParam;
import run.ut.app.model.support.BaseResponse;

public interface AdminService {

    String LOG_PATH = "logs/spring.log";

    /**
     * Login by email and other params
     * @param adminLoginParam email and code
     * @return user info
     */
    @NonNull
    UserDTO loginByEmail(@NonNull AdminLoginParam adminLoginParam);

    /**
     * Get user by email
     * @param email email
     * @return User domain
     */
    @NonNull
    User getUserByEmail(@NonNull String email);

    /**
     * Send verification code to email.
     * @param email email
     * @return Feedback text
     */
    @NonNull
    BaseResponse<String> sendEmailCode(@NonNull String email);

    /**
     * Get Application log
     * @param lines Log lines
     * @return Returns the log with the specified number of lines
     */
    @NonNull
    String getSpringLogsFiles(@NonNull Long lines);

}
