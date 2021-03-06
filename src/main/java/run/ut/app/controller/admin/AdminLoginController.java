package run.ut.app.controller.admin;

import cn.hutool.core.lang.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.admin.AdminLoginControllerApi;
import run.ut.app.cache.lock.HttpRequestRateLimit;
import run.ut.app.exception.BadRequestException;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.enums.RateLimitEnum;
import run.ut.app.model.param.EmailLoginParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.AdminService;

/**
 * admin login controller
 *
 * @author wenjie
 */


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("admin")
public class AdminLoginController implements AdminLoginControllerApi {

    private final AdminService adminService;

    @Override
    @HttpRequestRateLimit(limit = RateLimitEnum.RRLimit_1_5)
    @PostMapping("loginByEmail")
    public UserDTO loginByEmail(EmailLoginParam emailLoginParam) {
        Assert.hasText(emailLoginParam.getEmail(), "email must not be blank");
        Assert.hasText(emailLoginParam.getCode(), "code must not be blank");

        boolean isEmail = Validator.isEmail(emailLoginParam.getEmail());
        if (!isEmail) {
            throw new BadRequestException("非法邮箱！");
        }

        return adminService.loginByEmail(emailLoginParam);
    }

    @Override
    @HttpRequestRateLimit(limit = RateLimitEnum.RRLimit_1_10)
    @PostMapping("sendEmailCode")
    public BaseResponse<String> sendEmailCode(String email) {
        Assert.hasText(email, "email must not be blank");
        boolean isEmail = Validator.isEmail(email);
        if (!isEmail) {
            throw new BadRequestException("非法邮箱！");
        }
        return adminService.sendEmailCode(email);
    }
}
