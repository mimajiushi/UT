package run.ut.app.elasticsearch;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.ut.app.elasticsearch.handler.EsSyncHandlers;
import run.ut.app.model.properties.BinLogProperties;
import run.ut.app.service.OptionsService;
import run.ut.app.utils.SpringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author wenjie
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class BinLogListener implements BinaryLogClient.EventListener, ApplicationListener<ContextRefreshedEvent> {

    private final EsSyncHandlers esSyncHandlers;
    private final TableTemplate tableTemplate;
    private final DataSourceProperties dataSourceProperties;
    private final NacosServiceDiscovery nacosServiceDiscovery;
    private final NacosServiceRegistry nacosServiceRegistry;
    private final NacosDiscoveryProperties nacosDiscoveryProperties;
    private final ApplicationContext context;
    private BinaryLogClient client;

    @Value("${spring.datasource.host}")
    private String host;
    @Value("${spring.datasource.port}")
    private Integer port;

    @SneakyThrows
    @Override
    public void onEvent(Event event) {
        if (event != null && event.getData() != null) {
            log.debug(event.getData().toString());
        }
        EventType eventType = event.getHeader().getEventType();
        if (eventType == EventType.TABLE_MAP){
            TableMapEventData tableMapEventData = event.getData();
            EsSyncHandlers.database = tableMapEventData.getDatabase();
            EsSyncHandlers.table = tableMapEventData.getTable();
            return;
        }
        if (!tableTemplate.getColumnMap().containsKey(EsSyncHandlers.table)) {
            return;
        }
        EventData data = event.getData();
        esSyncHandlers.sync(data);
    }


    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String serviceName = this.getClass().getSimpleName();
        List<ServiceInstance> instances = nacosServiceDiscovery.getInstances(serviceName);
        if(CollectionUtils.isEmpty(instances)) {
            register(serviceName);
        } else {
            return;
        }
        Thread thread = new Thread(() -> {
            try {
                binlogClientBoot();
            } catch (Exception e) {
                closeBinlogClient();
                e.printStackTrace();
            }
        });
        thread.setDaemon(false);
        thread.start();
    }

    public void binlogClientBoot() throws IOException {
        BinLogListener binLogListener = SpringUtils.getBean(BinLogListener.class);
        OptionsService optionsService = SpringUtils.getBean(OptionsService.class);
        Optional<Object> binlogFileName = optionsService.getByKey(BinLogProperties.BINLOG_FILENAME.getValue());
        Optional<Object> binlogPosition = optionsService.getByKey(BinLogProperties.BINLOG_POSITION.getValue());
        client = new BinaryLogClient(
            host,
            port,
            dataSourceProperties.getUsername(),
            dataSourceProperties.getPassword()
        );
        client.registerEventListener(binLogListener);
        binlogFileName.ifPresent(o -> client.setBinlogFilename(String.valueOf(o)));
        binlogPosition.ifPresent(o -> client.setBinlogPosition(Long.parseLong(String.valueOf(o))));

        client.connect();
    }

    public void closeBinlogClient() throws IOException {
        deregister(this.getClass().getSimpleName());
        client.disconnect();
    }

    private void register(String serviceName) {
        nacosServiceRegistry.register(buildNacosRegistration(serviceName));
    }

    private void deregister(String serviceName) {
        nacosServiceRegistry.deregister(buildNacosRegistration(serviceName));
    }

    private NacosRegistration buildNacosRegistration(String serviceName) {
        NacosDiscoveryProperties newNacosDiscoveryProperties = new NacosDiscoveryProperties();
        BeanUtils.copyProperties(nacosDiscoveryProperties, newNacosDiscoveryProperties);
        newNacosDiscoveryProperties.setService(serviceName);
        return new NacosRegistration(newNacosDiscoveryProperties, context);
    }
}
