package elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import run.ut.app.UtServiceCenterApplication;
import run.ut.app.model.domain.Tags;
import run.ut.app.model.elasticsearch.ESPosts;
import run.ut.app.repository.EsPostRepository;
import run.ut.app.elasticsearch.TableTemplate;
import run.ut.app.utils.JsonUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtServiceCenterApplication.class)
@Slf4j
public class elasticsearchTest {

    @Resource
    private EsPostRepository esPostRepository;
//    @Resource
//    private TableTemplate tableTemplate;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

//    /**
//     * 插入
//     */
//    @Test
//    public void insertPost() {
//        List<ESPosts> esPosts = new ArrayList<>();
//        esPosts.add(ESPosts.builder().id(1L).content("http").forumId(1L).uid(1L).title("java").likes(1L).build());
//        esPosts.add(ESPosts.builder().id(11L).content("http").forumId(1L).uid(1L).title("java").likes(1L).build());
//        esPosts.add(ESPosts.builder().id(111L).content("http").forumId(1L).uid(1L).title("java").likes(1L).build());
//        esPosts.add(ESPosts.builder().id(2L).content("dubbo").forumId(1L).uid(1L).title("alibaba").likes(1L).build());
//        esPosts.add(ESPosts.builder().id(3L).content("netty").forumId(1L).uid(1L).title("永远滴神").likes(1L).build());
//        esPosts.add(ESPosts.builder().id(4L).content("quic").forumId(1L).uid(1L).title("udp协议").likes(1L).build());
//        esPosts.add(ESPosts.builder().id(5L).content("程文杰").forumId(1L).uid(1L).title("广州大学华软软件学院").likes(1L).build());
//        Iterable<ESPosts> esPosts1 = esPostRepository.saveAll(esPosts);
//        for (ESPosts posts : esPosts1) {
//            log.info(posts.toString());
//        }
//    }
//
//    /**
//     * 查询全部
//     */
//    @Test
//    public void queryAllPost() {
//        Iterable<ESPosts> all = esPostRepository.findAll();
//        for (ESPosts esPosts : all) {
//            log.info(esPosts.toString());
//        }
//    }
//
//    /**
//     * 删除
//     */
//    @Test
//    public void deletePosts() {
//        esPostRepository.delete(ESPosts.builder().id(1L).build());
//    }
//
//    /**
//     * 条件模糊查询
//     */
//    @Test
//    public void queryByParam() {
//        // 无法使用空格，写接口时记得去除空格
//        PageRequest pageRequest = PageRequest.of(0, 10, Sort.unsorted());
//        Page<ESPosts> res = esPostRepository.findByTitleContainingOrContentContaining("p", "p", pageRequest);
////        Page<ESPosts> res = esPostRepository.findByTitleContainingOrContentContaining(null, null, pageRequest);
//        if (CollectionUtils.isEmpty(res.toList())) {
//            log.error("无结果");
//        }
//        log.info("查询结果如下：\n");
//        for (ESPosts post : res) {
//            log.info(post.toString());
//        }
//    }
//
//    @Test
//    public void TableTemplateTest() {
//        List<String> columns = tableTemplate.getColumnMap().get("posts");
//        log.info("TableTemplateTest");
//        for (String column : columns) {
//            log.info(column);
//        }
//    }
//
//    /**
//     * 创建文档对象
//     */
//    @Test
//    public void createDocument() throws IOException {
//        Tags tags = Tags.builder().id(1).name("test").build();
//        IndexRequest indexRequest = new IndexRequest("new_index_1");
//
//        indexRequest.id(tags.getId()+"");
//        indexRequest.source(JsonUtils.objectToJson(tags), XContentType.JSON);
//
//        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
//        System.out.println(index.status());
//    }

    @Test
    public void search() {
        String keyWord = "wdawdawd";
//        String keyWord = "\uD83C\uDF4E";
        Long fid = null;
        Pageable page = PageRequest.of(0, 15);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withPageable(page);
        if (ObjectUtils.allNotNull(fid)) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.termQuery("forum_id", fid));
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }
        if (StringUtils.isNoneBlank(keyWord)) {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("title", keyWord),
                ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("content", keyWord),
                ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders =
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        // 排序，按相关度
        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<ESPosts> searchHits = elasticsearchRestTemplate.search(searchQuery, ESPosts.class);
        if (searchHits.getTotalHits() <= 0) {
            log.info("无查询结果：");
        } else {
            List<ESPosts> esPosts = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
            log.info("查询结果如下：");
            for (ESPosts esPost : esPosts) {
                log.info(esPost.toString());
            }
        }
    }
}
