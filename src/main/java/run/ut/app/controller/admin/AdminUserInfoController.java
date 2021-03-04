package run.ut.app.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.admin.AdminUserInfoControllerApi;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.UserInfoService;

import java.io.IOException;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("admin")
@CheckAuthorization(roles = UserRolesEnum.ROLE_ADMIN)
public class AdminUserInfoController implements AdminUserInfoControllerApi {

    private final UserInfoService userInfoService;

    @Override
    @PostMapping("verifyUserInfo")
    public BaseResponse<String> verifyUserInfo(String id, Integer status, String reason) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(status, "status must not be null");
        return userInfoService.verifyUserInfo(id, status, reason);
    }


    @Override
    @GetMapping("listUserInfoByParam")
    public CommentPage<UserInfoDTO> listUserInfoByParam(UserInfoParam userInfoParam,
                                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) throws IOException {
        Page<UserInfo> page = new Page<>(pageNum, pageSize);
        return userInfoService.listUserInfoByParam(userInfoParam, page);
    }
}
