package run.ut.base;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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

    public final String ADMIN_ACCOUNT = "1498780478@qq.com";
    public final String TOURIST_ACCOUNT = "chenwenjie@bytedance.com";
    public final String STUDENT_ACCOUNT = "1599603313@qq.com";


    private final String CODE = "123456";

    /**
     * 缓存管理员登陆邮箱+验证码
     */
    public void cacheAdminLogin() {
        String key = String.format("ADMIN_EMAIL_LOGIN::%s", ADMIN_ACCOUNT);
        String key2 = String.format(USER_EMAIL_LOGIN, ADMIN_ACCOUNT);
        redisService.set(key, CODE);
        redisService.set(key2, CODE);
    }

    /**
     * 缓存游客登陆邮箱+验证码
     */
    public void cacheTouristLogin() {
        String key = String.format(USER_EMAIL_LOGIN, TOURIST_ACCOUNT);
        redisService.set(key, CODE);
    }

    /**
     * 缓存学生登陆邮箱+验证码
     */
    public void cacheStudentLogin() {
        String key = String.format(USER_EMAIL_LOGIN, STUDENT_ACCOUNT);
        redisService.set(key, CODE);
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
        return login(ADMIN_ACCOUNT, CODE);
    }

    /**
     * 登陆游客账号
     *
     * @see #login
     */
    public UserDTO loginByTourist() throws Exception {
        return login(TOURIST_ACCOUNT, CODE);
    }

    /**
     * 登陆学生账号
     *
     * @see #login
     */
    public UserDTO loginByStudent() throws Exception {
        return login(STUDENT_ACCOUNT, CODE);
    }


    /**
     * 获取管理员Token
     *
     * @see #login
     */
    public String getAdminToken() throws Exception {
        return loginByAdmin().getToken().getAccessToken();
    }

    /**
     * 获取游客Token
     *
     * @see #login
     */
    public String getTouristToken() throws Exception {
        return loginByTourist().getToken().getAccessToken();
    }
    /**
     * 获取学生Token
     *
     * @see #login
     */
    public String getStudentToken() throws Exception {
        return loginByStudent().getToken().getAccessToken();
    }








}
