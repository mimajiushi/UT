package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.ut.app.exception.AlreadyExistsException;
import run.ut.app.exception.AuthenticationException;
import run.ut.app.exception.BadRequestException;
import run.ut.app.model.domain.TeamsMembers;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.mapper.UserTeamApplyLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.enums.ApplyModeEnum;
import run.ut.app.model.enums.ApplyStatusEnum;
import run.ut.app.model.enums.TeamsMemberEnum;
import run.ut.app.model.param.DealInvitationOrApplyParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ApplyOrInviteMsgVO;
import run.ut.app.service.UserTeamApplyLogService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  UserTeamApplyLogServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserTeamApplyLogServiceImpl extends ServiceImpl<UserTeamApplyLogMapper, UserTeamApplyLog> implements UserTeamApplyLogService {

    private final UserTeamApplyLogMapper userTeamApplyLogMapper;
    private final TeamsMembersServiceImpl teamsMembersService;

    @Override
    public CommentPage<ApplyOrInviteMsgVO> listUserApplyMsg(Long uid, Integer status, Page page) {
        IPage<ApplyOrInviteMsgVO> applyOrInviteMsgVOIPage = userTeamApplyLogMapper.listUserApplyMsg(page, uid, status);
        List<ApplyOrInviteMsgVO> records = applyOrInviteMsgVOIPage.getRecords();
        long total = applyOrInviteMsgVOIPage.getTotal();
        fillRecruitmentName(records);
        return new CommentPage<>(total, records);
    }

    @Override
    public CommentPage<ApplyOrInviteMsgVO> listUserInviteMsg(Long uid, Integer status, Page page) {
        IPage<ApplyOrInviteMsgVO> applyOrInviteMsgVOIPage = userTeamApplyLogMapper.listUserInviteMsg(page, uid, status);
        List<ApplyOrInviteMsgVO> records = applyOrInviteMsgVOIPage.getRecords();
        long total = applyOrInviteMsgVOIPage.getTotal();
        fillRecruitmentName(records);
        return new CommentPage<>(total, records);
    }

    @Override
    public CommentPage<ApplyOrInviteMsgVO> listTeamApplyMsg(List<Long> teamIds, Integer status, Page page) {
        IPage<ApplyOrInviteMsgVO> applyOrInviteMsgVOIPage = userTeamApplyLogMapper.listTeamApplyMsg(page, teamIds, status);
        List<ApplyOrInviteMsgVO> records = applyOrInviteMsgVOIPage.getRecords();
        long total = applyOrInviteMsgVOIPage.getTotal();
        fillRecruitmentName(records);
        return new CommentPage<>(total, records);
    }

    @Override
    public CommentPage<ApplyOrInviteMsgVO> listTeamInviteMsg(List<Long> teamIds, Integer status, Page page) {
        IPage<ApplyOrInviteMsgVO> applyOrInviteMsgVOIPage = userTeamApplyLogMapper.listTeamInviteMsg(page, teamIds, status);
        List<ApplyOrInviteMsgVO> records = applyOrInviteMsgVOIPage.getRecords();
        long total = applyOrInviteMsgVOIPage.getTotal();
        fillRecruitmentName(records);
        return new CommentPage<>(total, records);
    }

    @Override
    @Transactional
    public BaseResponse<String> userDealWithInvitation(Long uid, DealInvitationOrApplyParam param) {
        Long[] ids = param.getIds();
        String message = param.getMessage();
        Integer status = param.getStatus();
        ApplyStatusEnum statusEnum = ApplyStatusEnum.getByType(status);
        // check ids
        List<UserTeamApplyLog> userTeamApplyLogs = this.baseMapper.selectList(new QueryWrapper<UserTeamApplyLog>()
                .in("id", Arrays.asList(ids))
                .eq("mode", ApplyModeEnum.TEAM_TO_USER.getType())
                .eq("status", ApplyStatusEnum.WAITING.getType()));
        if (userTeamApplyLogs.size() != ids.length){
            throw new BadRequestException("ids参数有误");
        }

        List<TeamsMembers> teamsMembers = new ArrayList<>(ids.length);

        // check team's member
        for (int i = 0; i < userTeamApplyLogs.size(); i++){
            UserTeamApplyLog userTeamApplyLog = userTeamApplyLogs.get(i);
            Long teamId = userTeamApplyLog.getTeamId();
            Integer count = teamsMembersService.countByUid(uid, teamId);
            checkAndSetMember(message, statusEnum, teamsMembers, userTeamApplyLog, count);
        }

        // save
        updateBatchById(userTeamApplyLogs);
        teamsMembersService.saveBatch(teamsMembers);
        return BaseResponse.ok("处理完成，处理结果：" + statusEnum.getName());
    }

    @Override
    @Transactional
    public BaseResponse<String> teamDealWithApplication(Long leaderId, DealInvitationOrApplyParam param) {
        Long[] ids = param.getIds();
        String message = param.getMessage();
        Integer status = param.getStatus();
        ApplyStatusEnum statusEnum = ApplyStatusEnum.getByType(status);

        // check ids
        List<UserTeamApplyLog> userTeamApplyLogs = this.baseMapper.selectList(new QueryWrapper<UserTeamApplyLog>()
                .in("id", Arrays.asList(ids))
                .eq("mode", ApplyModeEnum.USER_TO_TEAM.getType())
                .eq("status", ApplyStatusEnum.WAITING.getType()));
        if (userTeamApplyLogs.size() != ids.length){
            throw new BadRequestException("ids参数有误");
        }

        // check leader
        for (UserTeamApplyLog userTeamApplyLog : userTeamApplyLogs) {
            Long teamId = userTeamApplyLog.getTeamId();
            Integer count1 = teamsMembersService.countByLeaderId(leaderId, teamId);
            if (count1 < 1){
                throw new AuthenticationException("对不起，你没有操作权限！");
            }
        }

        List<TeamsMembers> teamsMembers = new ArrayList<>(ids.length);

        // check team's member
        for (int i = 0; i < userTeamApplyLogs.size(); i++){
            UserTeamApplyLog userTeamApplyLog = userTeamApplyLogs.get(i);
            Long teamId = userTeamApplyLog.getTeamId();
            Long uid = userTeamApplyLog.getUid();
            Integer count = teamsMembersService.countByUid(uid, teamId);
            checkAndSetMember(message, statusEnum, teamsMembers, userTeamApplyLog, count);
        }

        // save
        updateBatchById(userTeamApplyLogs);
        teamsMembersService.saveBatch(teamsMembers);
        return BaseResponse.ok("处理完成，处理结果：" + statusEnum.getName());
    }

    @Override
    public List<String> getCountThatWaitingStatus(Long uid, List<Long> teamIds) {
        List<String> countList = new ArrayList<>();
        // user -> apply -> team
        Integer count1 = this.baseMapper.selectCount(new QueryWrapper<UserTeamApplyLog>()
                .eq("uid", uid)
                .eq("mode", ApplyModeEnum.USER_TO_TEAM.getType())
                .eq("status", ApplyStatusEnum.WAITING.getType()));
        // team -> applied -> user
        Integer count2 = teamIds.size()>0?
                this.baseMapper.selectCount(new QueryWrapper<UserTeamApplyLog>()
                .eq("mode", ApplyModeEnum.USER_TO_TEAM.getType())
                .eq("status", ApplyStatusEnum.WAITING.getType())
                .in("team_id", teamIds))
                :0;
        // team -> invite -> user
        Integer count3 = teamIds.size()>0?
                this.baseMapper.selectCount(new QueryWrapper<UserTeamApplyLog>()
                        .eq("mode", ApplyModeEnum.TEAM_TO_USER.getType())
                        .eq("status", ApplyStatusEnum.WAITING.getType())
                        .in("team_id", teamIds))
                :0;
        // user -> invited -> team
        Integer count4 = this.baseMapper.selectCount(new QueryWrapper<UserTeamApplyLog>()
                .eq("uid", uid)
                .eq("mode", ApplyModeEnum.TEAM_TO_USER.getType())
                .eq("status", ApplyStatusEnum.WAITING.getType()));
        countList.add(count1.toString());
        countList.add(count2.toString());
        countList.add(count3.toString());
        countList.add(count4.toString());
        return countList;
    }

    private void checkAndSetMember(String message, ApplyStatusEnum statusEnum, List<TeamsMembers> teamsMembers, UserTeamApplyLog userTeamApplyLog, Integer count) {
        if (count > 0 && statusEnum == ApplyStatusEnum.PASS){
            throw new AlreadyExistsException("同意的申请/邀请中有已加入的团队！");
        }else {
            userTeamApplyLog.setStatus(statusEnum)
                    .setMessage(message)
                    .setUpdateTime(null);
            TeamsMembers teamsMember = new TeamsMembers().setTeamId(userTeamApplyLog.getTeamId()).
                    setUid(userTeamApplyLog.getUid()).
                    setIsLeader(TeamsMemberEnum.NORMAL);
            teamsMembers.add(teamsMember);
        }
    }


    private void fillRecruitmentName(List<ApplyOrInviteMsgVO> applyOrInviteMsgVOList){
        for(int i = 0; i < applyOrInviteMsgVOList.size(); i++){
            ApplyOrInviteMsgVO applyOrInviteMsgVO = applyOrInviteMsgVOList.get(i);
            Long id = applyOrInviteMsgVO.getRecruitmentId();
            if (id == 0){
                applyOrInviteMsgVO.setRecruitmentName("加入团队(不选择职位)");
            }
        }
    }
}
