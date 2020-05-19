package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.param.*;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ApplyOrInviteMsgVO;

import java.util.List;

/**
 * @author wenjie
 */

@Api(value = "队伍操作API",tags = "队伍操作API")
public interface TeamsControllerApi {

    @ApiOperation(value = "用户创建队伍", notes = "需要登录（Token）验证，且创建者至少通过学生级别或以上认证，创建完之后创建者默认会成为队长")
    TeamsDTO createTeam(TeamsParam teamsParam);

    @ApiOperation(value = "设置团队标签", notes = "需要登录（Token）验证，且只有队长才可以设置团队标签")
    List<TagsDTO> saveTeamsTags(String[] tagIds, Long teamsId);

    @ApiOperation(value = "更新团队logo", notes = "需要登录（Token）验证，只有队长才能更改团队logo")
    BaseResponse<String> updateTeamsLogo(@RequestPart("logo") MultipartFile logo, Long teamsId);

    @ApiOperation(value = "更新团队基本信息，如团队名、团队描述、状态等（logo、标签除外）")
    BaseResponse<String> updateTeamsBaseInfo(TeamsParam teamsParam);

    @ApiOperation(value = "设置招聘的职位", notes = "新增/更新，需要登录验证，只有队长才能设置")
    BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(TeamsRecruitmentsParam teamsRecruitmentsParam);

    @ApiOperation(value = "设置招聘职位的标签", notes = "需要登录（Token）验证，只有队长才能设置")
    List<TagsDTO> saveTeamsRecruitmentsTags(String[] tagIds, Long teamId, Long teamRecruitmentId);

    @ApiOperation(value = "用户申请加入团队", notes = "需要登录（Token）验证，且必须通过认证")
    BaseResponse<String> userApplyToTeam(TeamApplyOrInviteParam teamApplyParam);

    @ApiOperation(value = "团队邀请用户加入", notes = "需要登录（Token）验证、通过认证、队长身份才能操作")
    BaseResponse<String> teamInvitesUser(TeamApplyOrInviteParam teamInviteParam);

    @ApiOperation(value = "获取用户历史申请的列表", notes = "需要用户登录（Token）")
    CommentPage<ApplyOrInviteMsgVO> listUserApplyMsg(Integer pageNum, Integer pageSize, Integer status);

    @ApiOperation(value = "获取用户收到的邀请列表", notes = "需要用户登录（Token）")
    CommentPage<ApplyOrInviteMsgVO> listUserInviteMsg(Integer pageNum, Integer pageSize, Integer status);

    @ApiOperation(value = "获取团队收到的申请列表", notes = "需要用户登录（Token）,必须是队长身份")
    CommentPage listTeamApplyMsg(Integer pageNum, Integer pageSize, Integer status);

    @ApiOperation(value = "获取团队历史邀请列表", notes = "需要用户登录（Token）, 必须是队长身份")
    CommentPage<ApplyOrInviteMsgVO> listTeamInviteMsg(Integer pageNum, Integer pageSize, Integer status);

    @ApiOperation(value = "获取各申请/邀请中处于待处理状态的数量", notes = "需要用户登录（Token）")
    List<String> getCountThatWaitingStatus();

    @ApiOperation(value = "获取用户所属队伍列表", notes = "需要用户登录Token")
    List<TeamsDTO> listTeamsByUser();

    @ApiOperation(value = "用户处理团队发送给自己的邀请", notes = "需要用户登录（Token）")
    BaseResponse<String> userDealWithInvitation(DealInvitationOrApplyParam param);

    @ApiOperation(value = "队长处理收到的用户申请（加入团队的申请）", notes = "需要队长登录Token")
    BaseResponse<String> teamDealWithApplication(DealInvitationOrApplyParam param);

    @ApiModelProperty(value = "踢人/离队", notes = "mode字段说明：1-队长踢人 2-成员主动退队")
    BaseResponse<String> leave(LeaveParam leaveParam);
}
