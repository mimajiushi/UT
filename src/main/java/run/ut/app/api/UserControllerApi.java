package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.UserDTO;
import run.ut.app.model.dto.UserExperiencesDTO;
import run.ut.app.model.dto.UserInfoDTO;
import run.ut.app.model.param.UserExperiencesParam;
import run.ut.app.model.param.UserInfoParam;
import run.ut.app.model.param.UserParam;
import run.ut.app.model.support.BaseResponse;

import javax.validation.Valid;
import java.util.List;


@Api(value="用户信息API",tags = "用户操作API", description = "用户登录、注册、完善信息、更改信息等操作")
public interface UserControllerApi {

    @ApiOperation(value = "用户登录（网页端）", notes = "用户首次登录即可自动注册，无需手动注册")
    public UserDTO webPageLogin(UserParam userParam);

    @ApiOperation("发送短信验证码接口")
    public BaseResponse<String> sendSms(String phoneNumber);

    @ApiOperation(value = "认证申请", notes = "role字段说明：3-学生认证  5-导师认证  6-赛事主办方认证")
    public BaseResponse<UserInfoDTO> applyForCertification(UserInfoParam userInfoParam,
                                                           @RequestPart("file_front") @ApiParam( name = "file_front", value = "证件照正面") MultipartFile credentialsPhotoFront,
                                                           @RequestPart("file_reverse") @ApiParam(name = "file_reverse", value = "证件照反面") MultipartFile credentialsPhotoReverse) throws Exception;

    @ApiOperation(value = "用户设置自己标签", notes = "传入标签id，该接口需要登录（Token）且用户通过认证")
    public List<TagsDTO> saveUserTags(String[] tagIds) throws Exception;

    @ApiOperation(value = "用户添加/更新经历", notes = "该接口需要登录（Token）")
    public UserExperiencesDTO saveUserExperiences(UserExperiencesParam userExperiencesParam);

    @ApiOperation(value = "用户删除经历", notes = "该接口需要登录（Token）")
    public BaseResponse<String> deleteUserExperiences(String id);


}
