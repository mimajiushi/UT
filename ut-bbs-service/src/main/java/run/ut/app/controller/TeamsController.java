package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.api.TeamsControllerApi;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.handler.FileHandlers;
import run.ut.app.model.domain.Teams;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.enums.ApplyStatusEnum;
import run.ut.app.model.enums.TeamsStatusEnum;
import run.ut.app.model.param.*;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.support.UploadResult;
import run.ut.app.model.vo.ApplyOrInviteMsgVO;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.TeamsRecruitmentsService;
import run.ut.app.service.TeamsService;
import run.ut.app.service.UserInfoService;
import run.ut.app.service.UserTeamApplyLogService;
import run.ut.app.utils.ImageUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author wenjie
 */

@RestController
@Slf4j
@RequestMapping("teams")
public class TeamsController extends BaseController implements TeamsControllerApi {

    @DubboReference private UserInfoService userInfoService;
    @DubboReference private TeamsService teamsService;
    @DubboReference private TeamsRecruitmentsService teamsRecruitmentsService;
    @DubboReference private UserTeamApplyLogService userTeamApplyLogService;
    @Resource private FileHandlers fileHandlers;

    @Override
    @PostMapping("createTeam")
    @CheckLogin
    public TeamsDTO createTeam(@RequestBody TeamsParam teamsParam) {
        long leaderId = getUid();
        checkUser(leaderId);
        if (ObjectUtils.isEmpty(TeamsStatusEnum.getByType(teamsParam.getStatus()))) {
            throw new BadRequestException("团队发布状态参数有误！");
        }
        return teamsService.createTeam(teamsParam, leaderId);
    }

    @Override
    @PostMapping("saveTeamsTags")
    @CheckLogin
    public List<TagsDTO> saveTeamsTags(@RequestBody String[] tagIds, Long teamsId) {
        Long leaderId = getUid();
        checkUser(leaderId);
        return teamsService.saveTeamsTags(tagIds, leaderId, teamsId);
    }

    @Override
    @PostMapping("updateTeamsLogo")
    @CheckLogin
    public BaseResponse<String> updateTeamsLogo(@RequestPart("logo") MultipartFile logo, Long teamsId) {
        Long leaderId = getUid();
        checkUser(leaderId);
        if (!ImageUtils.isImage(logo)) {
            throw new BadRequestException("只接受图片格式文件！");
        }
        String logoPath = fileHandlers.upload(logo).getFilePath();
        return teamsService.updateTeamsLogo(logoPath, leaderId, teamsId);
    }

    @Override
    @PostMapping("updateTeamsBaseInfo")
    @CheckLogin
    public BaseResponse<String> updateTeamsBaseInfo(@RequestBody TeamsParam teamsParam) {
        Assert.notNull(teamsParam.getId(), "team id must not be null.");
        userInfoService.checkUser(getUid());
        teamsParam.setTagIds(null);
        return teamsService.updateTeamsBaseInfo(teamsParam, getUid());
    }

    @Override
    @CheckLogin
    @PostMapping("saveTeamsRecruitment")
    public BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(@RequestBody @Valid TeamsRecruitmentsParam teamsRecruitmentsParam) {
        Long uid = getUid();
        checkUser(uid);
        teamsService.getAndCheckTeamByLeaderIdAndTeamId(uid, teamsRecruitmentsParam.getTeamId());
        return teamsRecruitmentsService.saveTeamsRecruitment(teamsRecruitmentsParam);
    }

    @Override
    @CheckLogin
    @PostMapping("remove/recruitment/{teamId:\\d+}/{recruitmentId:\\d+}")
    public BaseResponse<TeamsRecruitmentsDTO> removeRecruitment(@PathVariable Long teamId, @PathVariable Long recruitmentId) {
        Teams teams = teamsService.getTeamByLeaderIdAndTeamId(getUid(), teamId);
        if (ObjectUtils.isEmpty(teams)) {
            throw new AuthenticationException("队伍不存在或无权操作队伍");
        }
        return teamsRecruitmentsService.removeRecruitment(teamId, recruitmentId);
    }

    @Override
    @CheckLogin
    @PostMapping("saveTeamsRecruitmentsTags")
    @Deprecated
    public List<TagsDTO> saveTeamsRecruitmentsTags(@RequestBody String[] tagIds, Long teamId, Long teamRecruitmentId) {
        Long uid = getUid();
        checkUser(uid);
        teamsService.getAndCheckTeamByLeaderIdAndTeamId(uid, teamId);
        return teamsService.saveTeamsRecruitmentsTags(tagIds, teamRecruitmentId);
    }

    @Override
    @PostMapping("userApplyToTeam")
    @CheckLogin
    public BaseResponse<String> userApplyToTeam(@RequestBody @Valid TeamApplyOrInviteParam teamApplyParam) {
        Long uid = getUid();
        checkUser(uid);
        teamApplyParam.setUid(uid);
        return teamsService.userApplyToTeam(teamApplyParam);
    }

    @Override
    @PostMapping("teamInvitesUser")
    @CheckLogin
    public BaseResponse<String> teamInvitesUser(@RequestBody @Valid TeamApplyOrInviteParam teamInviteParam) {
        Long leaderId = getUid();
        teamsService.getAndCheckTeamByLeaderIdAndTeamId(leaderId, teamInviteParam.getTeamId());
        UserInfo userInfo = userInfoService.getOneActivatedByUid(teamInviteParam.getUid());
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new AuthenticationException("只能邀请通过认证的用户！");
        }
        return teamsService.teamInvitesUser(teamInviteParam);
    }

    @Override
    @CheckLogin
    @GetMapping("listUserApplyMsg")
    public CommentPage<ApplyOrInviteMsgVO> listUserApplyMsg(@RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            Integer status) {
        Long uid = getUid();
        Page page = new Page(pageNum, pageSize);
        return userTeamApplyLogService.listUserApplyMsg(uid, status, page);
    }

    @Override
    @CheckLogin
    @GetMapping("listUserInviteMsg")
    public CommentPage<ApplyOrInviteMsgVO> listUserInviteMsg(@RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                             Integer status) {
        Long uid = getUid();
        Page page = new Page(pageNum, pageSize);
        return userTeamApplyLogService.listUserInviteMsg(uid, status, page);
    }

    @Override
    @CheckLogin
    @GetMapping("listTeamApplyMsg")
    public CommentPage<ApplyOrInviteMsgVO> listTeamApplyMsg(@RequestParam(defaultValue = "1") Integer pageNum,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            Integer status) {
        Long leaderId = getUid();
        List<Long> teamIds = teamsService.getTeamIdsByLeaderId(leaderId);
        if (teamIds.size() == 0) {
            return CommentPage.emptyPage();
        }
        Page page = new Page(pageNum, pageSize);
        return userTeamApplyLogService.listTeamApplyMsg(teamIds, status, page);
    }

    @Override
    @CheckLogin
    @GetMapping("listTeamInviteMsg")
    public CommentPage<ApplyOrInviteMsgVO> listTeamInviteMsg(@RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                             Integer status) {
        Long leaderId = getUid();
        List<Long> teamIds = teamsService.getTeamIdsByLeaderId(leaderId);
        if (teamIds.size() == 0) {
            return CommentPage.emptyPage();
        }
        Page page = new Page(pageNum, pageSize);
        return userTeamApplyLogService.listTeamInviteMsg(teamIds, status, page);
    }

    @Override
    @CheckLogin
    @GetMapping("getCountThatWaitingStatus")
    public List<String> getCountThatWaitingStatus() {
        Long uid = getUid();
        List<Long> teamIds = teamsService.getTeamIdsByLeaderId(uid);
        return userTeamApplyLogService.getCountThatWaitingStatus(uid, teamIds);
    }

    @Override
    @CheckLogin
    @GetMapping("listTeamsByUser")
    public List<TeamsDTO> listTeamsByUser() {
        return teamsService.listTeamsByUid(getUid());
    }

    @Override
    @PostMapping("userDealWithInvitation")
    @CheckLogin
    public BaseResponse<String> userDealWithInvitation(@Valid @RequestBody DealInvitationOrApplyParam param) {
        ApplyStatusEnum statusEnum = ApplyStatusEnum.getByType(param.getStatus());
        if (ObjectUtils.isEmpty(statusEnum) || statusEnum == ApplyStatusEnum.WAITING) {
            throw new BadRequestException("status参数有误！");
        }
        Long uid = getUid();

        return userTeamApplyLogService.userDealWithInvitation(uid, param);
    }

    @Override
    @PostMapping("teamDealWithApplication")
    @CheckLogin
    public BaseResponse<String> teamDealWithApplication(@Valid @RequestBody DealInvitationOrApplyParam param) {
        ApplyStatusEnum statusEnum = ApplyStatusEnum.getByType(param.getStatus());
        if (ObjectUtils.isEmpty(statusEnum) || statusEnum == ApplyStatusEnum.WAITING) {
            throw new BadRequestException("status参数有误！");
        }
        Long leaderId = getUid();

        return userTeamApplyLogService.teamDealWithApplication(leaderId, param);
    }

    @Override
    @PostMapping("leave")
    @CheckLogin
    public BaseResponse<String> leave(@RequestBody @Valid LeaveParam leaveParam) {
        leaveParam.setUid(getUid());
        return teamsService.leave(leaveParam);
    }

    @Override
    @PostMapping("transferLeader")
    @CheckLogin
    public BaseResponse<String> transferLeader(Long targetUid, Long teamId) {
        Assert.notNull(targetUid, "targetUid must not be null");
        Assert.notNull(teamId, "teamId must not be null");
        return teamsService.transferLeader(getUid(), targetUid, teamId);
    }

    @Override
    @PostMapping("disband/{teamId:\\d+}")
    @CheckLogin
    public BaseResponse<String> disband(@PathVariable Long teamId) {
        return teamsService.disband(getUid(), teamId);
    }


    /**
     * 检验用户是否通过认证
     * @param uid user's id
     */
    private void checkUser(Long uid) {
        UserInfo userInfo = userInfoService.getOneActivatedByUid(uid);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new AuthenticationException("只有通过认证的用户才能创建团队！");
        }
    }
}
