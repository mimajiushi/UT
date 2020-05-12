package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.PostPhotos;
import run.ut.app.mapper.PostPhotosMapper;
import run.ut.app.service.PostPhotosService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  PostPhotosServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostPhotosServiceImpl extends ServiceImpl<PostPhotosMapper, PostPhotos> implements PostPhotosService {

}
