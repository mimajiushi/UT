package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.mail.MailService;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.AdminLoginParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.token.AuthToken;
import run.ut.app.security.util.JwtOperator;
import run.ut.app.service.AdminService;
import run.ut.app.service.RedisService;
import run.ut.app.service.UserService;
import run.ut.app.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final RedisService redisService;
    private final JwtOperator jwtOperator;
    private final MailService mailService;


    @Override
    public UserDTO loginByEmail(AdminLoginParam adminLoginParam) {
        String email = adminLoginParam.getEmail();
        String code = adminLoginParam.getCode();

        // check code
        String redisKey = RedisConfig.EMAIL_LOGIN_PREFIX + "::" + email;
        String resCode = redisService.get(redisKey);
        if (!code.equals(resCode)) {
            throw new AuthenticationException("无效帐号或验证码！");
        }

        // check roles
        User user = userService.getUserByEmail(email);
        if (ObjectUtils.isEmpty(user)) {
            throw new AuthenticationException("帐号不存在！");
        }
        int type = UserRolesEnum.ROLE_ADMIN.getType();
        if ((type & user.getRoles()) != type) {
            throw new AuthenticationException("没有相关权限");
        }

        // generate token
        AuthToken authToken = jwtOperator.buildAuthToken(user);
        return new UserDTO().convertFrom(user).setToken(authToken);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public BaseResponse<String> sendEmailCode(String email) {

        // check roles
        User user = getUserByEmail(email);
        int adminType = UserRolesEnum.ROLE_ADMIN.getType();
        if (ObjectUtils.isEmpty(user) || (adminType & user.getRoles()) != adminType) {
            throw new AuthenticationException("非管理员邮箱！");
        }

        // set code ttl and send email
        String key = RedisConfig.EMAIL_LOGIN_PREFIX + "::" + email;
        String code = RandomUtils.number(6);
        redisService.setKeyValTTL(key, code, RedisConfig.EMAIL_CODE_TIME_OUT);

        Map<String, Object> data = new HashMap<>();
        data.put("code", code);
        data.put("nickname", user.getNickname());

        String templates = "mail_template/mail_login.ftl";
        String subject = "UT管理员登录验证";
        mailService.sendTemplateMail(email, subject, data, templates);

        return BaseResponse.ok("发送验证码成功");
    }
}
