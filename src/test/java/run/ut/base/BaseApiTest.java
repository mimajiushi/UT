package run.ut.base;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.BeforeTest;
import run.ut.app.UtApplication;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.param.EmailLoginParam;
import run.ut.app.service.RedisService;
import run.ut.app.utils.JsonUtils;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.ut.app.config.redis.RedisKey.USER_EMAIL_LOGIN;

/**
 * Base api test.
 *
 * @author johnniang
 */
@SpringBootTest(
        classes = UtApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("not-websocket")
@Slf4j
@AutoConfigureMockMvc
public class BaseApiTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RedisService redisService;

    private final String LOGIN_PATH = "/user/loginByEmail";

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public void cacheAdminLogin() {
        // 设置邮箱缓存
        String key = "ADMIN_EMAIL_LOGIN::1498780478@qq.com";
        String key2 = String.format(USER_EMAIL_LOGIN, "1498780478@qq.com");
        String value = "123456";
        redisService.set(key, value);
        redisService.set(key2, value);
    }

    /**
     * 登陆
     *
     * @param email       账号邮箱
     * @param code        验证码
     * @return            用户信息（包括Token）
     * @throws Exception  mock异常
     */
    public UserDTO login(String email, String code) throws Exception {
        EmailLoginParam emailLoginParam = new EmailLoginParam();
        emailLoginParam.setEmail(email);
        emailLoginParam.setCode(code);

        MvcResult mvcResult = mvc.perform(
                post(LOGIN_PATH)
                        .content(JsonUtils.objectToJson(emailLoginParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return JsonUtils.mapToObject(JsonPath.read(response, "$.data"), UserDTO.class);
    }

    /**
     * 登陆管理员账号
     *
     * @see #login
     */
    public UserDTO loginByAdmin() throws Exception {
        return login("1498780478@qq.com", "123456");
    }

    /**
     * 获取管理员Token
     *
     * @see #login
     */
    public String getAdminToken() throws Exception {
        return loginByAdmin().getToken().getAccessToken();
    }








}
