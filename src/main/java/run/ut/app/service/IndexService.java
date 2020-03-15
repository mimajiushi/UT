package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.param.SearchTeamParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;
import run.ut.app.model.vo.TeamVO;

/**
 * @author wenjie
 */


public interface IndexService {

    /**
     * list students by param
     * @return student vo list
     */
    CommentPage<StudentVO> listStudentByParam(SearchStudentParam searchStudentParam, Page page);

    /**
     * list teams by param
     * @return team vo list
     */
    CommentPage<TeamVO> listTeamByParam(SearchTeamParam searchTeamParam, Page page);

    /**
     * @return Student's non-private information
     */
    StudentVO showStudentPage(Long uid);

}
