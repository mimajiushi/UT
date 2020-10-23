package run.ut.app.sync;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import run.ut.app.mapper.ExMapper;
import run.ut.app.scan.DocumentScan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TableTemplate
 *
 * @author wenjie
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TableTemplate implements ApplicationListener<ContextRefreshedEvent> {

    private final DocumentScan documentScan;
    private final ExMapper exMapper;

    @Value("${spring.datasource.database}")
    private String database;

    /**
     * table -> columns
     */
    @Getter
    private Map<String, List<String>> columnMap = new HashMap<>();


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initColumnMap();
    }

    /**
     * init columnMap
     */
    private void initColumnMap() {
        List<String> indexNames = documentScan.getIndexNames();
        for (String indexName : indexNames) {
            List<String> columns = exMapper.selectColumnNameByDatabaseAndTable(database, indexName);
            columnMap.put(indexName, columns);
        }
    }
}
