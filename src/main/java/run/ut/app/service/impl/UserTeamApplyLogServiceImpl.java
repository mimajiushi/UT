package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.mapper.UserTeamApplyLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ApplyOrInviteMsgVO;
import run.ut.app.service.UserTeamApplyLogService;

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
