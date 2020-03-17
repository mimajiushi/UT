package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.ut.app.model.domain.UserTeamApplyLog;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
