package run.ut.app.controller;


import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.api.UserControllerApi;
import run.ut.app.config.wechat.WechatAccountConfig;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.FileOperationException;
import run.ut.app.exception.WeChatException;
import run.ut.app.model.domain.User;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.enums.SexEnum;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.*;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.WeChatResponse;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.security.CheckLogin;
import run.ut.app.security.util.JwtOperator;
import run.ut.app.service.SmsService;
import run.ut.app.service.UserExperiencesService;
import run.ut.app.service.UserInfoService;
import run.ut.app.service.UserService;
import run.ut.app.utils.ImageUtils;
import run.ut.app.utils.RandomUtils;
import run.ut.app.utils.UtUtils;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("user")
public class UserController extends BaseController implements UserControllerApi {

    private final UserService userService;
    private final SmsService smsService;
    private final JwtOperator jwtOperator;
    private final UserInfoService userInfoService;
    private final UserExperiencesService userExperiencesService;
    private final RestTemplate restTemplate;
    private final WechatAccountConfig wechatAccountConfig;

    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize dataSize;

    @Override
    @PostMapping("webPageLogin")
    public UserDTO webPageLogin(@Valid UserParam userParam) {
        Assert.notNull(userParam.getSmsCode(), "Sms code must not be null");
        Assert.notNull(userParam.getPhoneNumber(), "Phone number must not be null");

        User user = userParam.convertTo();
        // check phone number
        int count = userService.count(new QueryWrapper<User>().eq("phone_number", userParam.getPhoneNumber()));
        if (!Validator.isMobile(userParam.getPhoneNumber() + "")) {
            throw new BadRequestException("非法手机号！");
        }
        if (count > 0) {
            // login
            smsService.checkCode(userParam.getPhoneNumber(), userParam.getSmsCode());
            user = userService.getOne(new QueryWrapper<User>().eq("phone_number", userParam.getPhoneNumber()));
            UserDTO userDTO = new UserDTO().convertFrom(user);
            userDTO.setToken(jwtOperator.buildAuthToken(user));
            return userDTO;
        } else {
            // register and login
            smsService.checkCode(userParam.getPhoneNumber(), userParam.getSmsCode());

            user.setNickname("UT_" + UtUtils.randomUUIDWithoutDash());
            user.setSex(SexEnum.UNKNOW);
            user.setRoles(UserRolesEnum.ROLE_TOURIST.getType());
            user.setAvatar("https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg");
            userService.save(user);

            UserDTO userDTO = new UserDTO().convertFrom(user);
            userDTO.setToken(jwtOperator.buildAuthToken(user));
            return userDTO;
        }
    }

    @Override
    @PostMapping("wechatLogin")
    public UserDTO wechatLogin(@RequestBody @Valid WeChatLoginParam weChatLoginParam) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("appid", wechatAccountConfig.getMpAppId());
        params.put("secret", wechatAccountConfig.getMpAppSecret());
        params.put("js_code", weChatLoginParam.getCode());
        params.put("grant_type", wechatAccountConfig.getGrantType());
        URIBuilder builder = new URIBuilder(wechatAccountConfig.getAuthorizeUrl());
        for (String key : params.keySet()) {
            builder.addParameter(key, params.get(key));
        }
        URI uri = builder.build();
        ResponseEntity<WeChatResponse> res = restTemplate.getForEntity(uri, WeChatResponse.class);
        int statusCodeValue = res.getStatusCodeValue();
        if (200 != statusCodeValue) {
            throw new WeChatException("微信授权接口请求错误！请联系管理员！");
        }
        WeChatResponse weChatResponse = res.getBody();
        return userService.wechatLogin(weChatLoginParam, weChatResponse);
    }

    @Override
    @PostMapping("sendSms")
    public BaseResponse<String> sendSms(String phoneNumber) {
        Assert.notNull(phoneNumber, "Phone number must not be null");
        if (!Validator.isMobile(phoneNumber + "")) {
            throw new BadRequestException("非法手机号！");
        }
        smsService.sendCode(phoneNumber, RandomUtils.number(6));
        return BaseResponse.ok("发送验证码成功！");
    }

    @Override
    @GetMapping("showSelfPage")
    @CheckLogin
    public StudentVO showSelfPage() {
        Long uid = getUid();
        return userService.showSelfPage(uid);
    }

    @Override
    @PostMapping("applyForCertification")
    @CheckLogin
    public BaseResponse<UserInfoDTO> applyForCertification(@Valid @RequestBody UserInfoParam userInfoParam) throws Exception {
        userInfoParam.setUid(getUid());
        long size1 = userInfoParam.getCredentialsPhotoFront().length();
        long size2 = userInfoParam.getCredentialsPhotoReverse().length();
        long realSize1 = size1 - (size1 / 8) * 2;
        long realSize2 = size2 - (size2 / 8) * 2;
        long maxFileSize = dataSize.toBytes();
        if (size1 > maxFileSize || size2 > maxFileSize) {
            throw new BadRequestException("文件大小不可超过10Mb");
        }

        return userInfoService.applyForCertification(userInfoParam);
    }

    @Override
    @PostMapping("saveUserTags")
    @CheckLogin
    public List<TagsDTO> saveUserTags(@RequestBody String[] tagIds) throws Exception {
        long uid = Long.parseLong(request.getAttribute("uid") + "");
        if (tagIds.length > 3) {
            throw new BadRequestException("标签数量不能 > 3 个！");
        }

        return userService.saveUserTags(uid, tagIds);
    }

    @Override
    @PostMapping("saveUserExperiences")
    @CheckLogin
    public UserExperiencesDTO saveUserExperiences(@Valid @RequestBody UserExperiencesParam userExperiencesParam) {
        long uid = getUid();
        userExperiencesParam.setUid(uid);
        return userExperiencesService.saveUserExperiences(userExperiencesParam);
    }

    @Override
    @PostMapping("deleteUserExperiences")
    @CheckLogin
    public BaseResponse<String> deleteUserExperiences(String id) {
        long uid = getUid();
        return userExperiencesService.deleteUserExperiences(uid, id);
    }

    @Override
    @PostMapping("updateUserSimpleInfo")
    @CheckLogin
    public BaseResponse<String> updateUserSimpleInfo(@Valid @RequestBody UserSimpleParam userSimpleParam) {
        userSimpleParam.setUid(getUid());
        userService.updateById(userSimpleParam.convertTo()
                .setSex(SexEnum.getByType(userSimpleParam.getSex())));
        return BaseResponse.ok("更新成功");
    }

    @Override
    @CheckLogin
    @PostMapping("updateUserAvatar")
    public BaseResponse<String> updateUserAvatar(@RequestPart("avatar") MultipartFile avatar) {
        if (!ImageUtils.isImage(avatar)) {
            throw new FileOperationException("只接受图片格式文件！");
        }
        return userService.updateUserAvatar(getUid(), avatar);
    }
}
