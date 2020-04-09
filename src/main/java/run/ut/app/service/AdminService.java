package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.AdminLoginParam;
import run.ut.app.model.support.BaseResponse;

public interface AdminService {

    String LOG_PATH = "logs/spring.log";

    @NonNull
    UserDTO loginByEmail(@NonNull AdminLoginParam adminLoginParam);

    @NonNull
    User getUserByEmail(@NonNull String email);

    @NonNull
    BaseResponse<String> sendEmailCode(@NonNull String email);

    @NonNull
    String getSpringLogsFiles(@NonNull Long lines);

}
