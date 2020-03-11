package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.TagsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.TagsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  UserTagsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Override
    @Transactional
    public BaseResponse<TagsDTO> saveTag(TagsParam tagsParam) {
        Tags tags = tagsParam.convertTo();
        // update if id != null
        if (tags.getId() != null){
            Tags oldTag = getById(tags.getId());
            if (ObjectUtils.isEmpty(oldTag)){
                throw new NotFoundException("未找到指定标签，更新标签失败！tag_id="+tags.getId()+" tag_name" + tags.getName());
            }
            oldTag.setName(tags.getName())
                    .setParentId(tags.getParentId())
                    .setUpdateTime(null);
            if (null != tags.getParentId() || tags.getParentId() > 0){
                oldTag.setLevel(getById(tags.getParentId()).getLevel()+1);
            }else {
                oldTag.setLevel(0);
            }
            tags = oldTag;
        }

        // insert if id == null
        if (null != tags.getParentId() && tags.getParentId() > 0){
            tags.setLevel(getById(tags.getParentId()).getLevel()+1);
        }else {
            tags.setLevel(0);
        }
        saveOrUpdate(tags);
        return BaseResponse.ok("设置标签成功！", new TagsDTO().convertFrom(tags));
    }

    @Override
    public List<TagsDTO> listTagsByParentId(@NonNull Integer parentId) {
        return list(new QueryWrapper<Tags>().eq("parent_id", parentId))
                .stream().map(e -> {
                    return (TagsDTO)new TagsDTO().convertFrom(e);
        }).collect(Collectors.toList());
    }
}
