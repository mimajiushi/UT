package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.param.TeamsParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

@Api(value="队伍操作API",tags = "队伍操作API")
public interface TeamsControllerApi {

    @ApiOperation(value = "用户创建队伍", notes = "需要登录（Token）验证，且创建者至少通过学生级别或以上认证，创建完之后创建者默认会成为队长")
    public TeamsDTO createTeam(TeamsParam teamsParam, @RequestPart("logo") MultipartFile logo);

    @ApiOperation(value = "设置团队标签", notes = "需要登录（Token）验证，且只有队长才可以设置团队标签")
    public List<TagsDTO> saveTeamsTags(String[] tagIds);

    @ApiOperation(value = "更新团队logo", notes = "需要登录（Token）验证，只有队长才能更改团队logo")
    public BaseResponse<String> updateTeamsLogo(@RequestPart("logo") MultipartFile logo);


}
