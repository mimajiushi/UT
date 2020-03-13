package run.ut.app.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.ut.app.api.admin.AdminUserInfoControllerAPI;
import run.ut.app.model.support.AuthorizeRoles;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckAuthorization;
import run.ut.app.service.UserInfoService;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("admin")
public class AdminUserInfoController implements AdminUserInfoControllerAPI {

    private final UserInfoService userInfoService;

    @Override
    @CheckAuthorization(roles = AuthorizeRoles.ROLE_ADMIN)
    @PostMapping("verifyUserInfo")
    public BaseResponse<String> verifyUserInfo(Integer id, Integer status, String reason) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(id, "status must not be null");
        return userInfoService.verifyUserInfo(id, status, reason);
    }
}
