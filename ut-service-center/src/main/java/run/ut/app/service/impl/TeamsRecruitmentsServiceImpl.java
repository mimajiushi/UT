package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.TeamsRecruitmentsMapper;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.param.TeamsRecruitmentsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.TeamsRecruitmentsService;
import run.ut.app.utils.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *  TeamsRecruitmentsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-13
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DubboService
public class TeamsRecruitmentsServiceImpl extends ServiceImpl<TeamsRecruitmentsMapper, TeamsRecruitments> implements TeamsRecruitmentsService {

    @Override
    @Transactional
    public BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(@NotNull TeamsRecruitmentsParam teamsRecruitmentsParam) {

        TeamsRecruitments teamsRecruitments = teamsRecruitmentsParam.convertTo();

        // update
        Long teamsRecruitmentsId = teamsRecruitments.getId();
        if (null != teamsRecruitmentsId) {
            TeamsRecruitments teamsRecruitments2 = getById(teamsRecruitmentsId);
            BeanUtils.updateProperties(teamsRecruitments, teamsRecruitments2);
            teamsRecruitments2.setUpdateTime(null);
            updateById(teamsRecruitments2);
            return BaseResponse.ok("更新职位信息成功！", new TeamsRecruitmentsDTO().convertFrom(teamsRecruitments2));
        }

        // insert
        save(teamsRecruitments);
        return BaseResponse.ok("新增职位信息成功！", new TeamsRecruitmentsDTO().convertFrom(teamsRecruitments));
    }

    @Override
    public List<TeamsRecruitments> listTeamsRecruitmentsByTeamsId(@NotNull Long teamsId) {
        return list(new QueryWrapper<TeamsRecruitments>().eq("team_id", teamsId));
    }

    @Override
    public BaseResponse<TeamsRecruitmentsDTO> removeRecruitment(Long teamId, Long recruitmentId) {
        TeamsRecruitments teamsRecruitments = getOne(new QueryWrapper<TeamsRecruitments>().eq("id", recruitmentId).eq("team_id", teamId));
        if (ObjectUtils.isEmpty(teamsRecruitments)) {
            throw new NotFoundException("职位记录不存在！");
        }
        boolean remove = removeById(teamsRecruitments.getId());
        return remove ? BaseResponse.ok("删除成功",
            new TeamsRecruitmentsDTO().convertFrom(teamsRecruitments)) : BaseResponse.ok("请稍后重试");
    }
}
