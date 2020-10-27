package run.ut.app.elasticsearch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Scan @Document annotation and save
 *
 * @author wenjie
 */
@Component
@Slf4j
@Getter
public class DocumentScan implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

    private List<String> indexNames = new ArrayList<>();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        log.debug("【run.ut.app.scan.DocumentScan.postProcessBeanDefinitionRegistry】");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Map<String, Object> beansWithAnnotation = configurableListableBeanFactory.getBeansWithAnnotation(Document.class);
        log.info("scan @Document and register");
        for (Map.Entry<String, Object> stringObjectEntry : beansWithAnnotation.entrySet()) {
            indexNames.add(stringObjectEntry.getValue().getClass().getAnnotation(Document.class).indexName());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
