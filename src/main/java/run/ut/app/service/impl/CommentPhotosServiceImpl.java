package run.ut.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import run.ut.app.model.domain.CommentPhotos;
import run.ut.app.mapper.CommentPhotosMapper;
import run.ut.app.service.CommentPhotosService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  CommentPhotosServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentPhotosServiceImpl extends ServiceImpl<CommentPhotosMapper, CommentPhotos> implements CommentPhotosService {

}
