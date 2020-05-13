package run.ut.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.ut.app.exception.NotFoundException;
import run.ut.app.mapper.PostsMapper;
import run.ut.app.model.domain.PostPhotos;
import run.ut.app.model.domain.Posts;
import run.ut.app.model.param.PostParam;
import run.ut.app.model.support.BaseResponse;
import run.ut.app.service.PostPhotosService;
import run.ut.app.service.PostsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  PostsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    private final PostPhotosService postPhotosService;

    @Override
    @Transactional
    public BaseResponse<String> savePost(PostParam postParam) {
        boolean insert = (null == postParam.getId());
        List<String> photos = postParam.getPhotos();
        if (insert) {
            Posts posts = postParam.convertTo();
            save(posts);
            List<PostPhotos> postPhotosList = photos.stream().map(url ->
                new PostPhotos().setPostId(posts.getId()).setPhoto(url)).collect(Collectors.toList());
            postPhotosService.saveBatch(postPhotosList);
        } else {
            Posts posts = getById(postParam.getId());
            if (ObjectUtils.isEmpty(posts)) {
                throw new NotFoundException("帖子不存在！");
            }
            postParam.update(posts);
            posts.setUpdateTime(null);
            postPhotosService.remove(new QueryWrapper<PostPhotos>().eq("post_id", posts.getId()));
            List<PostPhotos> postPhotosList = photos.stream().map(url ->
                new PostPhotos().setPostId(posts.getId()).setPhoto(url)).collect(Collectors.toList());
            updateById(posts);
            postPhotosService.saveBatch(postPhotosList);
        }
        return BaseResponse.ok("发布成功~");
    }
}
