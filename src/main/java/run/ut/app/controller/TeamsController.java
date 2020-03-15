package run.ut.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.ut.app.api.TeamsControllerApi;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.exception.FileOperationException;
import run.ut.app.model.domain.UserInfo;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.enums.TeamsStatusEnum;
import run.ut.app.model.param.TeamsParam;
import run.ut.app.model.param.TeamsRecruitmentsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.security.CheckLogin;
import run.ut.app.service.TeamsRecruitmentsService;
import run.ut.app.service.TeamsService;
import run.ut.app.service.UserInfoService;
import run.ut.app.utils.ImageUtils;

import javax.validation.Valid;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("teams")
public class TeamsController extends BaseController implements TeamsControllerApi {

    private final UserInfoService userInfoService;
    private final TeamsService teamsService;
    private final TeamsRecruitmentsService teamsRecruitmentsService;

    @Override
    @PostMapping("createTeam")
    @CheckLogin
    public TeamsDTO createTeam(TeamsParam teamsParam, @RequestPart("logo") MultipartFile logo) {
        long leaderId = getUid();
        checkUser(leaderId);
        if (ObjectUtils.isEmpty(TeamsStatusEnum.getByType(teamsParam.getStatus()))){
            throw new BadRequestException("团队发布状态参数有误！");
        }
        return teamsService.createTeam(teamsParam, leaderId, logo);
    }

    @Override
    @PostMapping("saveTeamsTags")
    @CheckLogin
    public List<TagsDTO> saveTeamsTags(String[] tagIds, Long teamsId) {
        Long leaderId = getUid();
        checkUser(leaderId);
        return teamsService.saveTeamsTags(tagIds, leaderId, teamsId);
    }

    @Override
    @PostMapping("updateTeamsLogo")
    @CheckLogin
    public BaseResponse<String> updateTeamsLogo(@RequestPart("logo") MultipartFile logo, Long teamsId){
        Long leaderId = getUid();
        checkUser(leaderId);

        if (!ImageUtils.isImage(logo)){
            throw new FileOperationException("只接受图片格式文件！");
        }
        return teamsService.updateTeamsLogo(logo, leaderId, teamsId);
    }

    @Override
    @CheckLogin
    @PostMapping("saveTeamsRecruitment")
    public BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(@Valid TeamsRecruitmentsParam teamsRecruitmentsParam) {
        Long uid = getUid();
        checkUser(uid);
        teamsService.getAndCheckTeamByLeaderIdAndTeamId(uid, teamsRecruitmentsParam.getTeamId());
        return teamsRecruitmentsService.saveTeamsRecruitment(teamsRecruitmentsParam);
    }

    @Override
    @CheckLogin
    @PostMapping("saveTeamsRecruitmentsTags")
    public List<TagsDTO> saveTeamsRecruitmentsTags(String[] tagIds, Long TeamId, Long teamRecruitmentId) {
        Long uid = getUid();
        checkUser(uid);
        teamsService.getAndCheckTeamByLeaderIdAndTeamId(uid, teamRecruitmentId);
        return teamsService.saveTeamsRecruitmentsTags(tagIds, teamRecruitmentId);
    }

    /**
     * 检验用户是否通过认证
     * @param uid user's id
     */
    private void checkUser(Long uid){
        UserInfo userInfo = userInfoService.getOneActivatedByUid(uid);
        if (ObjectUtils.isEmpty(userInfo)){
            throw new AuthenticationException("只有通过认证的用户才能创建团队！");
        }
    }
}
