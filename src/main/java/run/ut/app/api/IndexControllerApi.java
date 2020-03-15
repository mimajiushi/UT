package run.ut.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.param.SearchTeamParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.model.vo.TeamVO;

import java.util.List;


@Api(value="用于返回首页等主页数据",tags = "主页数据核心API")
public interface IndexControllerApi {

    @ApiOperation(value = "获取首页的学生列表（找队友）")
    public CommentPage<StudentVO> listStudentByParam(SearchStudentParam searchStudentParam, Integer pageNum, Integer pageSize);

    @ApiOperation(value = "获取首页的团队列表（找团队）")
    public CommentPage<TeamVO> listTeamByParam(SearchTeamParam searchTeamParam, Integer pageNum, Integer pageSize);

    @ApiOperation(value = "学生展示给别人看的主页", notes = "非自己看自己的个人主页，不包含手机号等敏感信息")
    public StudentVO showStudentInfo(Long uid);

    @ApiOperation(value = "团队展示给别人看的主页", notes = "包含成员信息、团队经历、团队标签等")
    public TeamVO showTeamsInfo(Long teamsId);

}
