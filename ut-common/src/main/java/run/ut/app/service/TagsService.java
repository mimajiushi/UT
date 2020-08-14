package run.ut.app.service;

import org.springframework.lang.NonNull;
import run.ut.app.model.domain.Tags;
import com.baomidou.mybatisplus.extension.service.IService;
import run.ut.app.model.dto.TagsDTO;
import run.ut.app.model.param.TagsParam;
import run.ut.app.model.support.BaseResponse;

import java.util.List;

/**
 * <p>
 *  TagsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-11
 */
public interface TagsService extends IService<Tags> {

    /**
     * insert or update tags
     * @param tagsParam tags param
     * @return success message and dto
     */
    @NonNull
    public BaseResponse<TagsDTO> saveTag(@NonNull TagsParam tagsParam);

    /**
     * Get child tags, if parentId == 0, get top-level tags
     * @param parentId tag's parentId
     * @return list collection for TagsDTO
     */
    @NonNull
    public List<TagsDTO> listTagsByParentId(@NonNull Integer parentId);

}
