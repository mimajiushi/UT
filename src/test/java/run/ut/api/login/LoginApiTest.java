package run.ut.api.login;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.Test;
import run.ut.app.UtApplication;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.security.token.AuthToken;
import run.ut.base.BaseApiTest;

import static org.testng.Assert.assertEquals;
import static run.ut.utils.AssertUtil.*;

/**
 * @author chenwenjie.star
 * @date 2021/7/29 4:08 下午
 */
@SpringBootTest(
        classes = UtApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginApiTest extends BaseApiTest {

    /**
     * 管理员登陆校验
     */
    @Test
    public void adminLoginTest() throws Exception {
        // 使用包含全部信息的账号测试
        UserDTO userDTO = loginByAdmin();

        // 校验token信息
        AuthToken authToken = userDTO.getToken();
        assertNotBlank(authToken.getAccessToken(), "token不为空");
        assertNotNull(authToken.getExpirationTime(), "token过期时间不为空");

        // 校验返回的基本信息
        assertNotBlank(userDTO.getAvatar(), "头像地址为空");
        assertNotBlank(userDTO.getDescription(), "个人描述为空");
        assertNotBlank(userDTO.getEmail(), "绑定邮箱为空");
        assertNotBlank(userDTO.getNickname(), "昵称为空");
        assertNotBlank(userDTO.getOpenid(), "openid为空");
        assertNotNull(userDTO.getRoles(), "roles为空");
        assertNotNull(userDTO.getSex(), "性别为空");
        assertNotNull(userDTO.getUid(), "uid为空");
        assertCollectionNotEmpty(userDTO.getRolesName(), "角色名list为空");


        // 校验权限位图
        Integer roles = userDTO.getRoles();
        int adminRole = UserRolesEnum.ROLE_ADMIN.getValue();
        assertEquals(adminRole, (adminRole & roles), "管理员账号没有管理员权限");
    }

}
