package run.ut.app.listener;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import run.ut.app.config.redis.RedisKey;
import run.ut.app.model.domain.DataArea;
import run.ut.app.model.domain.DataSchool;
import run.ut.app.service.DataAreaService;
import run.ut.app.service.DataSchoolService;
import run.ut.app.service.RedisService;
import run.ut.app.utils.JsonUtils;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The method executed after the application is started.
 * May change to dynamic configuration in the future.
 * @author wenjie
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private final DataAreaService dataAreaService;
    private final DataSchoolService dataSchoolService;
    private final RedisService redisService;

    @Value("${server.port}")
    private Integer port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${logging.file.path}")
    private String logPath;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.initAreaData();
        this.printStartInfo();
    }

    private void printStartInfo() {
        String baseApiPath = "http://localhost:" + port +
                (null == contextPath ? "" : contextPath);
        log.info("UT-App started at         {}", baseApiPath);
        log.info("UT-App admin started at   {}/{}", baseApiPath, "admin/login");
        log.info("UT-App api doc was enabled at  {}/docs.html", baseApiPath);
        log.info("UT-App log file at   {}/spring.log", logPath);
        log.info("UT-App has started successfully!");
    }

    /**
     * Init area and school data
     */
    private void initAreaData() {
        // 数据加载线程池， https://www.jianshu.com/p/502f9952c09b
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(1,
                new ThreadFactoryBuilder().setNameFormat("data-company-loader").build());
        executorService.scheduleWithFixedDelay(() -> {
            /*
             * 加载所有省、市级信息
             */
            log.info("开始加载所有省、市级信息...");
            List<Integer> parentIdDistinct = dataAreaService.selectParentIdDistinct();
            for (Integer parentId : parentIdDistinct) {
                String key = RedisKey.AREA_PREFIX + "::" + parentId;
                List<DataArea> areasByParentId = dataAreaService.getAreaDataByParentId(parentId);
                redisService.setKeyValTTL(key, JsonUtils.objectToJson(areasByParentId), RedisKey.AREA_TTL);
            }
            log.info("加载所有省、市级信息结束...");

            /*
             * 加载学校信息缓存
             */
            log.info("开始加载校园数据...");
            List<Integer> provincIdDistinct = dataSchoolService.selectProvincIdDistinct();
            for (Integer provincId : provincIdDistinct) {
                String key = RedisKey.SCHOOL_DATA_LIST_PREFIX + "::" + provincId;
                List<DataSchool> listByProvinceId = dataSchoolService.getListByProvinceId(provincId);
                redisService.setKeyValTTL(key, JsonUtils.objectToJson(listByProvinceId), RedisKey.AREA_TTL);
            }

            List<DataSchool> allSchool = dataSchoolService.getAllSchool();
            for (DataSchool dataSchool : allSchool) {
                String key = RedisKey.SCHOOL_DATA_PREFIX + "::" + dataSchool.getId();
                redisService.setKeyValTTL(key, JsonUtils.objectToJson(dataSchool), RedisKey.AREA_TTL);
            }
            log.info("加载校园数据结束...");
        }, 0, 7, TimeUnit.DAYS);
    }

}
