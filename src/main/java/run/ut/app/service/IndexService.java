package run.ut.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import run.ut.app.model.param.SearchStudentParam;
import run.ut.app.model.support.CommentPage;
import run.ut.app.model.vo.StudentVO;

/**
 * @author wenjie
 */


public interface IndexService {

    /**
     * list student by param
     * @return student vo list
     */
    public CommentPage<StudentVO> listStudentByParam(SearchStudentParam searchStudentParam, Page page);

    /**
     * @return Student's non-private information
     */
    StudentVO showStudentPage(Long uid);

}
