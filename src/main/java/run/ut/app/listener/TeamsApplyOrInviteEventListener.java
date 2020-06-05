package run.ut.app.listener;


import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import run.ut.app.event.team.TeamsApplyOrInviteEvent;
import run.ut.app.mail.MailService;
import run.ut.app.model.domain.Teams;
import run.ut.app.model.domain.User;
import run.ut.app.model.enums.ApplyModeEnum;
import run.ut.app.model.enums.WebSocketMsgTypeEnum;
import run.ut.app.model.param.TeamApplyOrInviteParam;
import run.ut.app.netty.UserChannelManager;
import run.ut.app.service.TeamsMembersService;
import run.ut.app.service.TeamsService;
import run.ut.app.service.UserService;

import java.util.HashMap;

/**
 * @author wenjie
 */


@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamsApplyOrInviteEventListener {

    private final MailService mailService;
    private final TeamsService teamsService;
    private final TeamsMembersService teamsMembersService;
    private final UserService userService;
    private final UserChannelManager userChannelManager;

    private final String MAIL_INVITE = "mail_template/mail_invite.ftl";
    private final String MAIL_APPLY = "mail_template/mail_apply.ftl";


    /**
     * Receive team application or invitation event
     */
    @Async
    @EventListener
    public void handleTeamsApplyOrInviteEvent(TeamsApplyOrInviteEvent teamsApplyOrInviteEvent) throws JsonProcessingException {
        TeamApplyOrInviteParam teamApplyOrInviteParam = teamsApplyOrInviteEvent.getTeamApplyOrInviteParam();
        ApplyModeEnum applyModeEnum = teamsApplyOrInviteEvent.getApplyModeEnum();

        if (applyModeEnum == ApplyModeEnum.TEAM_TO_USER) {
            sendInvitationMsg(teamApplyOrInviteParam);
        } else if (applyModeEnum == ApplyModeEnum.USER_TO_TEAM) {
            sendApplicationMsg(teamApplyOrInviteParam);
        }
    }

    private void sendInvitationMsg(TeamApplyOrInviteParam teamApplyOrInviteParam) throws JsonProcessingException {
        Long teamId = teamApplyOrInviteParam.getTeamId();
        Teams team = teamsService.getById(teamId);
        User user = userService.getById(teamApplyOrInviteParam.getUid());
        String msg = String.format("团队【%s】邀请您加入~", team.getName());
        String subject = "【UT】入队邀请通知";

        if (ObjectUtil.isAllEmpty(team, user)) {
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("nickname", user.getNickname());

        // Send msg to wechat app
        userChannelManager.writeAndFlush(
            teamApplyOrInviteParam.getUid(),
            msg,
            WebSocketMsgTypeEnum.RECEIVED_INVITATION
        );
        // Send msg to email
        if (Validator.isEmail(user.getEmail())) {
            mailService.sendTemplateMail(user.getEmail(), subject, params, MAIL_INVITE);
        }
    }

    private void sendApplicationMsg(TeamApplyOrInviteParam teamApplyOrInviteParam) throws JsonProcessingException {
        Long teamId = teamApplyOrInviteParam.getTeamId();
        Teams team = teamsService.getById(teamId);
        User user = teamsMembersService.getLeaderByTeamsId(team.getId());
        User leader = teamsMembersService.getLeaderByTeamsId(teamId);
        String msg = String.format("用户【%s】申请加入您的【%s】队伍~", user.getNickname(), team.getName());
        String subject = "【UT】入队申请通知";

        if (ObjectUtil.isAllEmpty(team, user, leader)) {
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("msg", msg);
        params.put("nickname", leader.getNickname());

        // Send msg to wechat app
        userChannelManager.writeAndFlush(
            teamApplyOrInviteParam.getUid(),
            msg,
            WebSocketMsgTypeEnum.RECEIVED_APPLICATION
        );
        // Send msg to email
        if (Validator.isEmail(user.getEmail())) {
            mailService.sendTemplateMail(leader.getEmail(), subject, params, MAIL_APPLY);
        }
    }
}
