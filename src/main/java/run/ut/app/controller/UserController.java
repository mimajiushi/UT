package run.ut.app.controller;


import cn.hutool.core.lang.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.api.UserControllerApi;
import run.ut.app.cache.lock.HttpRequestRateLimit;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.WeChatException;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.enums.RateLimitEnum;
import run.ut.app.model.enums.SexEnum;
import run.ut.app.model.param.*;
import run.ut.app.model.properties.WechatMPProperties;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.WeChatResponse;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.OptionsService;
import run.ut.app.service.UserExperiencesService;
import run.ut.app.service.UserInfoService;
import run.ut.app.service.UserService;
import run.ut.app.utils.ImageUtils;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenjie
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("user")
public class UserController extends BaseController implements UserControllerApi {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final UserExperiencesService userExperiencesService;
    private final RestTemplate restTemplate;
    private final OptionsService optionsService;

    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize dataSize;

    @Override
    @PostMapping("wechatLogin")
    public UserDTO wechatLogin(@RequestBody @Valid WeChatLoginParam weChatLoginParam) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("appid", optionsService.getByPropertyOfNonNull(WechatMPProperties.MP_APP_ID).toString());
        params.put("secret", optionsService.getByPropertyOfNonNull(WechatMPProperties.MP_APP_SECRET).toString());
        params.put("js_code", weChatLoginParam.getCode());
        params.put("grant_type", optionsService.getByPropertyOfNonNull(WechatMPProperties.MP_GRANT_TYPE).toString());
        URIBuilder builder = new URIBuilder(optionsService.getByPropertyOfNonNull(WechatMPProperties.MP_AUTHORIZE_URL).toString());
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
    @PostMapping("loginByEmail")
    @HttpRequestRateLimit(limit = RateLimitEnum.RRLimit_1_5)
    public UserDTO emailLogin(EmailLoginParam emailLoginParam) {

        boolean isEmail = Validator.isEmail(emailLoginParam.getEmail());
        if (!isEmail) {
            throw new BadRequestException("非法邮箱！");
        }

        return userService.loginByEmail(emailLoginParam);
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
        if (realSize1 > maxFileSize || realSize2 > maxFileSize) {
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
            throw new BadRequestException("只接受图片格式文件！");
        }
        return userService.updateUserAvatar(getUid(), avatar);
    }

    @Override
    @CheckLogin
    @PostMapping("bindEmail")
    @HttpRequestRateLimit(limit = RateLimitEnum.RRLimit_1_5)
    public BaseResponse<String> bindEmail(String email, Integer code) {
        if (!Validator.isEmail(email)) {
            throw new BadRequestException("非法邮箱！");
        }
        if (null == code) {
            throw new BadRequestException("验证码不能为空！");
        }
        return userService.bindEmail(email, code, getUid());
    }

    @Override
    @PostMapping("sendEmailCode")
    @HttpRequestRateLimit(limit = RateLimitEnum.RRLimit_1_60)
    public BaseResponse<String> sendEmailCode(String email) {
        if (!Validator.isEmail(email)) {
            throw new BadRequestException("非法邮箱！");
        }
        // login
        try {
            getUid();
        } catch (Exception e) {
            return userService.sendEmailCode(email);
        }
        // bind email
        return userService.sendEmailCode(email, getUid());
    }
}
