package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.model.param.DealInvitationOrApplyParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.ApplyOrInviteMsgVO;

import java.util.List;

/**
 * <p>
 *  UserTeamApplyLogService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
public interface UserTeamApplyLogService extends IService<UserTeamApplyLog> {


    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listUserApplyMsg(@NonNull Long uid, @Nullable Integer status, Page page);

    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listUserInviteMsg(@NonNull Long uid, @Nullable Integer status, @NonNull Page page);

    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listTeamApplyMsg(@NonNull List<Long> teamIds, @Nullable Integer status, @NonNull Page page);

    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listTeamInviteMsg(@NonNull List<Long> teamIds, @Nullable Integer status, @NonNull Page page);

    @NonNull
    BaseResponse<String> userDealWithInvitation(@NonNull Long uid, @NonNull DealInvitationOrApplyParam param);

    @NonNull
    BaseResponse<String> teamDealWithApplication(@NonNull Long leaderId, @NonNull DealInvitationOrApplyParam param);

    @NonNull
    List<String> getCountThatWaitingStatus(@NonNull Long uid, @NonNull List<Long> teamIds);
}
