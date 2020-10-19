package elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.ut.app.UtServiceCenterApplication;
import run.ut.app.model.elasticsearch.ESPosts;
import run.ut.app.repository.EsPostRepository;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtServiceCenterApplication.class)
@Slf4j
public class elasticsearchTest {

    @Resource
    private EsPostRepository esPostRepository;

    /**
     * 插入
     */
    @Test
    public void insertPost() {
        ESPosts build = ESPosts.builder().id(1L).content("asdasd").forumId(1L).uid(1L).title("asdasd").likes(1L).build();
        ESPosts save = esPostRepository.save(build);
        log.info(save.toString());
    }

    /**
     * 查询全部
     */
    @Test
    public void queryAllPost() {
        Iterable<ESPosts> all = esPostRepository.findAll();
        for (ESPosts esPosts : all) {
            log.info(esPosts.toString());
        }
    }

    /**
     * 删除
     */
    @Test
    public void deletePosts() {
        esPostRepository.delete(ESPosts.builder().id(1L).build());
    }
}
