package run.ut.app.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import run.ut.app.model.elasticsearch.ESPosts;

/**
 *
 * @author wenjie
 */
public interface EsPostRepository extends ElasticsearchRepository<ESPosts, Long> {

}
