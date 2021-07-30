package run.ut.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.utils.JsonUtils;
import run.ut.base.BaseApiTest;
import run.ut.utils.AssertUtil;

import java.util.Map;

/**
 * @author chenwenjie.star
 * @date 2021/7/30 6:33 下午
 */
@Slf4j
@ActiveProfiles("not-websocket")
public class UserApiTest extends BaseApiTest {

    private final String SHOW_SELF_PAGE = "/user/showSelfPage";

    /**
     * 测试个人完整信息，使用信息齐全的账号
     */
    @Test
    public void showSelfPageTest(ITestContext context) throws Exception {
        String token = getAdminToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("UT-Token", token);

        BaseResponse response = httpRequest(SHOW_SELF_PAGE, headers, null, HttpMethod.GET);
        StudentVO studentVO = JsonUtils.mapToObject((Map<?, ?>) response.getData(), StudentVO.class);

        AssertUtil.assertEquals(response.getStatus().intValue(),  200, "响应值!=200");

        AssertUtil.assertNotNull(studentVO.getUid(), "uid must not be null");
        AssertUtil.assertNotBlank(studentVO.getNickname(), "nickname must not be blank");
        AssertUtil.assertNotBlank(studentVO.getRealName(), "realname must not be blank");
        AssertUtil.assertNotBlank(studentVO.getAvatar(), "avatar must not be blank");
        AssertUtil.assertNotNull(studentVO.getSex(), "sex must not be null");
        AssertUtil.assertNotNull(studentVO.getRoles(), "role must not be null");
        AssertUtil.assertNotBlank(studentVO.getPhoneNumber(), "phone number must not be null");
        AssertUtil.assertNotBlank(studentVO.getEmail(), "email must not be null");
        AssertUtil.assertNotNull(studentVO.getDegree(), "degree must not be null");
        AssertUtil.assertNotNull(studentVO.getGrade(), "grade must not be null");
        AssertUtil.assertNotBlank(studentVO.getSubject(), "subject must not be null");
        AssertUtil.assertNotBlank(studentVO.getSchool(), "school must not be blank");
        AssertUtil.assertNotBlank(studentVO.getDescription(), "description must not be blank");
        AssertUtil.assertNotBlank(studentVO.getCredentialsPhotoFront(),
                "credentialsPhotoFront must not be blank");
        AssertUtil.assertNotBlank(studentVO.getCredentialsPhotoReverse(),
                "credentialsPhotoReverse must not be blank");
        AssertUtil.assertTrue(studentVO.isHasAuthInfo(), "hasAuthInfo must be true");

        AssertUtil.assertCollectionNotEmpty(studentVO.getTags(), "tags must not be empty");
        for (TagsDTO tag : studentVO.getTags()) {
            AssertUtil.assertNotBlank(tag.getName(), "tag name must not be blank");
        }

        AssertUtil.assertCollectionNotEmpty(studentVO.getExperiences(), "experiences must not be empty");
        for (UserExperiencesDTO experiences : studentVO.getExperiences()) {
            AssertUtil.assertNotBlank(experiences.getName(), "experiences name must not be blank");
            AssertUtil.assertNotBlank(experiences.getAwards(), "experiences awards must not be blank");
            AssertUtil.assertNotBlank(experiences.getDescription(), "experiences description must not be blank");
            AssertUtil.assertNotBlank(experiences.getRole(), "experiences role must not be blank");
            AssertUtil.assertNotBlank(experiences.getProjectUrl(), "experiences url must not be blank");
            AssertUtil.assertNotBlank(experiences.getStartTime(), "experiences start time must not be blank");
            AssertUtil.assertNotBlank(experiences.getEndTime(), "experiences end time must not be blank");
            AssertUtil.assertNotNull(experiences.getId(), "experiences id must not be null");
            AssertUtil.assertNotNull(experiences.getUid(), "experiences uid must not be null");
        }
    }
}
