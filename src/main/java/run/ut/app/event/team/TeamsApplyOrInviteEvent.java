package run.ut.app.event.team;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import run.ut.app.model.enums.ApplyModeEnum;
import run.ut.app.model.param.TeamApplyOrInviteParam;

@Getter
@Setter
public class TeamsApplyOrInviteEvent extends ApplicationEvent {

    private TeamApplyOrInviteParam teamApplyOrInviteParam;
    private ApplyModeEnum applyModeEnum;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public TeamsApplyOrInviteEvent(Object source, TeamApplyOrInviteParam teamApplyOrInviteParam, ApplyModeEnum applyModeEnum) {
        super(source);
        this.teamApplyOrInviteParam = teamApplyOrInviteParam;
        this.applyModeEnum = applyModeEnum;
    }
}
