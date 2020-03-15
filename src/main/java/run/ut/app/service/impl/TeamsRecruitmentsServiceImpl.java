package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import run.ut.app.model.domain.TeamsRecruitments;
import run.ut.app.mapper.TeamsRecruitmentsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.dto.TeamsRecruitmentsDTO;
import run.ut.app.model.param.TeamsRecruitmentsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.TeamsRecruitmentsService;
import run.ut.app.utils.BeanUtils;

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
public class TeamsRecruitmentsServiceImpl extends ServiceImpl<TeamsRecruitmentsMapper, TeamsRecruitments> implements TeamsRecruitmentsService {

    @Override
    @Transactional
    public BaseResponse<TeamsRecruitmentsDTO> saveTeamsRecruitment(TeamsRecruitmentsParam teamsRecruitmentsParam) {

        TeamsRecruitments teamsRecruitments = teamsRecruitmentsParam.convertTo();

        // update
        Long teamsRecruitmentsId = teamsRecruitments.getId();
        if (null != teamsRecruitmentsId){
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
    public List<TeamsRecruitments> listTeamsRecruitmentsByTeamsId(Long teamsId) {
        return list(new QueryWrapper<TeamsRecruitments>().eq("team_id", teamsId));
    }
}