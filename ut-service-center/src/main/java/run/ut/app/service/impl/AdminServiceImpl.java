package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import run.ut.app.config.properties.UtProperties;
import run.ut.app.config.redis.RedisKey;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.ServiceException;
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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DubboService
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final RedisService redisService;
    private final JwtOperator jwtOperator;
    private final MailService mailService;
    private final UtProperties utProperties;


    @Override
    public UserDTO loginByEmail(AdminLoginParam adminLoginParam) {
        String email = adminLoginParam.getEmail();
        String code = adminLoginParam.getCode();

        // check code
        String redisKey = RedisKey.EMAIL_LOGIN_PREFIX + "::" + email;
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
        String key = RedisKey.EMAIL_LOGIN_PREFIX + "::" + email;
        String code = RandomUtils.number(6);
        redisService.setKeyValTTL(key, code, RedisKey.EMAIL_CODE_TIME_OUT);

        Map<String, Object> data = new HashMap<>();
        data.put("code", code);
        data.put("nickname", user.getNickname());

        String templates = "mail_template/mail_login.ftl";
        String subject = "UT管理员登录验证";
        mailService.sendTemplateMail(email, subject, data, templates);

        return BaseResponse.ok("发送验证码成功");
    }

    @Override
    public String getSpringLogsFiles(Long lines) {
        Assert.notNull(lines, "Lines must not be null");

        File file = new File(utProperties.getWorkDir(), LOG_PATH);

        List<String> linesArray = new ArrayList<>();

        StringBuilder result = new StringBuilder();

        if (!file.exists()) {
            return StringUtils.EMPTY;
        }
        long count = 0;

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            long length = randomAccessFile.length();
            if (length == 0L) {
                return StringUtils.EMPTY;
            } else {
                long pos = length - 1;
                while (pos > 0) {
                    pos--;
                    randomAccessFile.seek(pos);
                    if (randomAccessFile.readByte() == '\n') {
                        String line = randomAccessFile.readLine();
                        linesArray.add(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                        count++;
                        if (count == lines) {
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    randomAccessFile.seek(0);
                    linesArray.add(new String(randomAccessFile.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
            }
        } catch (Exception e) {
            throw new ServiceException("读取日志失败", e);
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.reverse(linesArray);

        linesArray.forEach(line -> {
            result.append(line)
                .append(StringUtils.LF);
        });

        return result.toString();
    }
}
