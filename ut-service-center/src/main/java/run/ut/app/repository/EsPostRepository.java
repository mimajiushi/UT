package run.ut.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import run.ut.app.model.elasticsearch.ESPosts;

/**
 *
 * @author wenjie
 */
public interface EsPostRepository extends ElasticsearchRepository<ESPosts, Long> {

    /**
     * Fuzzy query by title or content
     * @param title      title
     * @param content    content
     * @param pageable   pageable
     * @return           page of posts
     */
    Page<ESPosts> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
