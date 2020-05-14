package run.ut.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.ut.app.api.IndexControllerApi;
import run.ut.app.model.dto.TeamsDTO;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.enums.TeamsStatusEnum;
import run.ut.app.model.param.SearchRecruitmentParam;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.param.SearchTeamParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.model.vo.TeamVO;
import run.ut.app.model.vo.TeamsRecruitmentsVO;
import run.ut.app.service.IndexService;
import run.ut.app.service.TeamsService;
import run.ut.app.utils.MysqlEscapeUtils;

import java.util.List;

/**
 * @author wenjie
 */

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("index")
public class IndexController extends BaseController implements IndexControllerApi {

    private final IndexService indexService;
    private final TeamsService teamsService;

    @Override
    @GetMapping("listStudentByParam")
    public CommentPage<StudentVO> listStudentByParam(SearchStudentParam searchStudentParam,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Page page = new Page<>(pageNum, pageSize);

        return indexService.listStudentByParam(searchStudentParam, page);
    }

    @Override
    @GetMapping("listTeamByParam")
    public CommentPage<TeamVO> listTeamByParam(SearchTeamParam searchTeamParam,
                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        String name = searchTeamParam.getName();
        searchTeamParam.setName(MysqlEscapeUtils.escape(name));
        Page page = new Page(pageNum, pageSize);
        return indexService.listTeamByParam(searchTeamParam, page);
    }

    @Override
    @GetMapping("listRecruitmentByParam")
    public CommentPage<TeamsRecruitmentsVO> listRecruitmentByParam(SearchRecruitmentParam searchRecruitmentParam,
                                                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Page page = new Page(pageNum, pageSize);

        searchRecruitmentParam.setStatus(TeamsStatusEnum.PUBLIC.getType());
        return indexService.listRecruitmentByParam(searchRecruitmentParam, page);
    }

    @Override
    @GetMapping("teams/{leaderId:\\d+}")
    public List<TeamsDTO> listTeamsByLeaderId(@PathVariable Long leaderId) {
        return teamsService.listTeamsByLeaderId(leaderId);
    }

    @Override
    @GetMapping("recruitments/{teamId:\\d+}")
    public List<TeamsRecruitmentsDTO> listRecruitmentsByTeamId(@PathVariable Long teamId) {
        List<TeamsRecruitmentsDTO> teamsRecruitmentsDTOS = indexService.listRecruitmentsByTeamId(teamId);
        teamsRecruitmentsDTOS.add(new TeamsRecruitmentsDTO().setName("加入团队（不选择职位）").setTeamId(teamId).setId(0L));
        return teamsRecruitmentsDTOS;
    }

    @Override
    @GetMapping("/user/{uid:\\d+}")
    public StudentVO showUserPageInfo(@PathVariable Long uid) {
        return indexService.showUserPageInfo(uid);
    }

    @Override
    @GetMapping("/team/{teamsId:\\d+}")
    public TeamVO showTeamsInfo(@PathVariable Long teamsId) {
        return indexService.showTeamsInfo(teamsId);
    }

    @Override
    @GetMapping("/recruitment/{recruitmentsId:\\d+}")
    public TeamsRecruitmentsVO showRecruitmentsInfo(@PathVariable Long recruitmentsId) {
        return indexService.showRecruitmentsInfo(recruitmentsId);
    }


}
