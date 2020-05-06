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

    /**
     * 获取用户的申请列表
     *
     * @param uid      uid
     * @param page     mybatis分页对象
     * @param status   状态
     * @return         分页结果
     */
    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listUserApplyMsg(@NonNull Long uid, @Nullable Integer status, Page page);

    /**
     * 获取用户收到的邀请列表
     *
     * @param uid      uid
     * @param page     mybatis分页对象
     * @param status   状态
     * @return         分页结果
     */
    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listUserInviteMsg(@NonNull Long uid, @Nullable Integer status, @NonNull Page page);

    /**
     * 获取收到的入队申请列表
     *
     * @param teamIds  队伍id
     * @param page     mybatis分页对象
     * @param status   状态
     * @return         分页结果
     */
    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listTeamApplyMsg(@NonNull List<Long> teamIds, @Nullable Integer status, @NonNull Page page);

    /**
     * 获取发出过的入队邀请列表
     *
     * @param teamIds  队伍id
     * @param page     mybatis分页对象
     * @param status   状态
     * @return         分页结果
     */
    @NonNull
    CommentPage<ApplyOrInviteMsgVO> listTeamInviteMsg(@NonNull List<Long> teamIds, @Nullable Integer status, @NonNull Page page);

    /**
     * 用户处理邀请
     *
     * @param uid      uid
     * @param param    处理参数
     * @return         处理结果信息
     */
    @NonNull
    BaseResponse<String> userDealWithInvitation(@NonNull Long uid, @NonNull DealInvitationOrApplyParam param);

    /**
     * 队长处理申请
     *
     * @param leaderId  队长的uid
     * @param param     处理参数
     * @return          处理结果信息
     */
    @NonNull
    BaseResponse<String> teamDealWithApplication(@NonNull Long leaderId, @NonNull DealInvitationOrApplyParam param);

    /**
     * 获取所有未处理信息的数量，包括：用户发出的申请（未被处理）、用户收到的邀请（未处理）、队长收到申请（未处理）、队长发出的邀请（未被处理）
     *
     * @param uid         uid
     * @param teamIds     队伍id列表
     * @return            list 中的四个元素对应上面注释的四个
     */
    @NonNull
    List<String> getCountThatWaitingStatus(@NonNull Long uid, @NonNull List<Long> teamIds);
}
