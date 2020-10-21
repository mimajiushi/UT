package elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import run.ut.app.UtServiceCenterApplication;
import run.ut.app.model.elasticsearch.ESPosts;
import run.ut.app.repository.EsPostRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        List<ESPosts> esPosts = new ArrayList<>();
        esPosts.add(ESPosts.builder().id(1L).content("http").forumId(1L).uid(1L).title("java").likes(1L).build());
        esPosts.add(ESPosts.builder().id(11L).content("http").forumId(1L).uid(1L).title("java").likes(1L).build());
        esPosts.add(ESPosts.builder().id(111L).content("http").forumId(1L).uid(1L).title("java").likes(1L).build());
        esPosts.add(ESPosts.builder().id(2L).content("dubbo").forumId(1L).uid(1L).title("alibaba").likes(1L).build());
        esPosts.add(ESPosts.builder().id(3L).content("netty").forumId(1L).uid(1L).title("永远滴神").likes(1L).build());
        esPosts.add(ESPosts.builder().id(4L).content("quic").forumId(1L).uid(1L).title("udp协议").likes(1L).build());
        esPosts.add(ESPosts.builder().id(5L).content("程文杰").forumId(1L).uid(1L).title("广州大学华软软件学院").likes(1L).build());
        Iterable<ESPosts> esPosts1 = esPostRepository.saveAll(esPosts);
        for (ESPosts posts : esPosts1) {
            log.info(posts.toString());
        }
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

    /**
     * 条件模糊查询
     */
    @Test
    public void queryByParam() {
        // 无法使用空格，写接口时记得去除空格
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.unsorted());
//        Page<ESPosts> res = esPostRepository.findByTitleContainingOrContentContaining("p", "p", pageRequest);
        Page<ESPosts> res = esPostRepository.findByTitleContainingOrContentContaining(null, null, pageRequest);
        if (CollectionUtils.isEmpty(res.toList())) {
            log.error("无结果");
        }
        log.info("查询结果如下：\n");
        for (ESPosts post : res) {
            log.info(post.toString());
        }
    }
}
