package run.ut.app.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.UserControllerApi;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.enums.SexEnum;
import run.ut.app.model.param.UserParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.SmsService;
import run.ut.app.service.UserService;
import run.ut.app.utils.RandomUtils;
import run.ut.app.utils.UtUtils;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserControllerApi {

    private final UserService userService;
    private final SmsService smsService;

    @Override
    @PostMapping("webPageRegister")
    public UserDTO webPageRegister(@RequestBody @Valid UserParam userParam) {
        Assert.notNull(userParam.getSmsCode(), "Sms code must not be null");
        Assert.notNull(userParam.getPhoneNumber(), "Phone number must not be null");

        User user = userParam.convertTo();
        // check phone number
        int count = userService.count(new QueryWrapper<User>().eq("phone_number", userParam.getPhoneNumber()));
        if (!Validator.isMobile(userParam.getPhoneNumber() + "")){
            throw new BadRequestException("非法手机号！");
        }
        if (count > 0){
            throw new AlreadyExistsException("手机号已被注册");
        }

        smsService.checkCode(userParam.getPhoneNumber(), userParam.getSmsCode());

        user.setNickname("UT_" + UtUtils.randomUUIDWithoutDash());
        user.setSex(SexEnum.UNKNOW);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        userService.save(user);

        return new UserDTO().convertFrom(user);
    }

    @Override
    @PostMapping("sendSms")
    public BaseResponse<String> sendSms(String phoneNumber) {
        Assert.notNull(phoneNumber, "Phone number must not be null");
        if (!Validator.isMobile(phoneNumber + "")){
            throw new BadRequestException("非法手机号！");
        }
        smsService.sendCode(phoneNumber, RandomUtils.number(6));
        return BaseResponse.ok("发送验证码成功！");
    }
}
