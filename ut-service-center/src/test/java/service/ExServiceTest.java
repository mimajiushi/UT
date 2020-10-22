package service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.ut.app.UtServiceCenterApplication;
import run.ut.app.mapper.ExMapper;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtServiceCenterApplication.class)
@Slf4j
public class ExServiceTest {

    @Resource
    private ExMapper exMapper;

    @Test
    public void test1() {
        List<String> columns = exMapper.selectColumnNameByDatabaseAndTable("ut", "posts");
        System.out.println("表的数据列如下：");
        for (String column : columns) {
            System.out.println(column);
        }
    }
}
