package run.ut.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import run.ut.app.model.domain.UserTeamApplyLog;
import run.ut.app.model.vo.ApplyOrInviteMsgVO;

import java.util.List;

/**
 * <p>
 *  UserTeamApplyLogMapper
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
public interface UserTeamApplyLogMapper extends BaseMapper<UserTeamApplyLog> {
    IPage<ApplyOrInviteMsgVO> listUserApplyMsg(Page page,
                                               @Param("uid")Long uid, @Param("status")Integer status);

    IPage<ApplyOrInviteMsgVO> listUserInviteMsg(Page page,
                                               @Param("uid")Long uid, @Param("status")Integer status);

    IPage<ApplyOrInviteMsgVO> listTeamApplyMsg(Page page,
                                                 @Param("teamIds") List<Long> teamIds, @Param("status")Integer status);

    IPage<ApplyOrInviteMsgVO> listTeamInviteMsg(Page page,
                                                @Param("teamIds") List<Long> teamIds, @Param("status")Integer status);


}
