package run.ut.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.Zone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import run.ut.app.config.properties.UtProperties;
import run.ut.app.config.redis.RedisConfig;
import run.ut.app.event.options.OptionsUpdatedEvent;
import run.ut.app.exception.MissingPropertyException;
import run.ut.app.model.domain.Options;
import run.ut.app.mapper.OptionsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.ut.app.model.dto.OptionsDTO;
import run.ut.app.model.param.OptionsParam;
import run.ut.app.model.properties.PropertyEnum;
import run.ut.app.model.properties.QiniuOssProperties;
import run.ut.app.service.OptionsService;
import run.ut.app.service.RedisService;
import run.ut.app.utils.ServiceUtils;
import run.ut.app.utils.ValidationUtils;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * <p>
 *  OptionsServiceImpl
 * </p>
 *
 * @author wenjie
 * @since 2020-03-10
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService {

    private final OptionsMapper optionsMapper;
    private final ApplicationContext applicationContext;
    private final ApplicationEventPublisher eventPublisher;
    private final RedisService redisService;
    private final Map<String, PropertyEnum> propertyEnumMap = Collections.unmodifiableMap(PropertyEnum.getValuePropertyEnumMap());
    private final UtProperties utProperties;

    @Transactional
    void save(@NonNull String key, @Nullable String value) {
        Assert.hasText(key, "Option key must not be blank");
        save(Collections.singletonMap(key, value));
    }

    @Override
    public void save(Map<String, Object> optionMap) {
        if (CollectionUtils.isEmpty(optionMap)) {
            return;
        }

        Map<String, Options> optionKeyMap = ServiceUtils.convertToMap(list(), Options::getOptionKey);

        List<Options> optionsToCreate = new LinkedList<>();
        List<Options> optionsToUpdate = new LinkedList<>();

        optionMap.forEach((key, value) -> {
            Options oldOption = optionKeyMap.get(key);
            if (oldOption == null || !StringUtils.equals(oldOption.getOptionValue(), value.toString())) {
                OptionsParam optionParam = new OptionsParam();
                optionParam.setKey(key);
                optionParam.setValue(value.toString());
                ValidationUtils.validate(optionParam);

                if (oldOption == null) {
                    // Create it
                    optionsToCreate.add(optionParam.convertTo());
                } else if (!StringUtils.equals(oldOption.getOptionValue(), value.toString())) {
                    // Update it
                    optionParam.update(oldOption);
                    optionsToUpdate.add(oldOption);
                }
            }
        });

        // Update them
        updateBatchById(optionsToUpdate);

        // Create them
        saveBatch(optionsToCreate);

        if (!CollectionUtils.isEmpty(optionsToUpdate) || !CollectionUtils.isEmpty(optionsToCreate)) {
            // If there is something changed
            publishOptionUpdatedEvent();
        }
    }

    @Override
    public void save(List<OptionsParam> optionParams) {
        if (CollectionUtils.isEmpty(optionParams)) {
            return;
        }

        Map<String, Object> optionMap = ServiceUtils.convertToMap(optionParams, OptionsParam::getKey, OptionsParam::getValue);
        save(optionMap);
    }

    @Override
    public void save(OptionsParam optionParam) {
        Options option = optionParam.convertTo();
        publishOptionUpdatedEvent();
        save(option);
    }

    @Override
    public void update(@NotNull Integer optionId, @NotNull OptionsParam optionParam) {
        Options optionToUpdate = getById(optionId);
        optionParam.update(optionToUpdate);
        publishOptionUpdatedEvent();
        updateById(optionToUpdate);
    }

    @Override
    public void saveProperty(@NotNull PropertyEnum property, String value) {
        Assert.notNull(property, "Property must not be null");

        save(property.getValue(), value);
    }

    @Override
    public void saveProperties(@NotNull Map<? extends PropertyEnum, String> properties) {
        if (CollectionUtils.isEmpty(properties)) {
            return;
        }

        Map<String, Object> optionMap = new LinkedHashMap<>();

        properties.forEach((property, value) -> optionMap.put(property.getValue(), value));

        save(optionMap);
    }

    @Override
    public Map<String, Object> listOptions() {
        // Get options from cache
        String resJson = redisService.get(RedisConfig.OPTIONS_KEY);

        if (StringUtils.isBlank(resJson)) {
            List<Options> options = list();

            Set<String> keys = ServiceUtils.fetchProperty(options, Options::getOptionKey);

            Map<String, Object> userDefinedOptionMap = ServiceUtils.convertToMap(options, Options::getOptionKey, option -> {
                String key = option.getOptionKey();

                PropertyEnum propertyEnum = propertyEnumMap.get(key);

                if (propertyEnum == null) {
                    return option.getOptionValue();
                }

                return PropertyEnum.convertTo(option.getOptionValue(), propertyEnum);
            });

            Map<String, Object> result = new HashMap<>(userDefinedOptionMap);

            // Add default property
            propertyEnumMap.keySet()
                    .stream()
                    .filter(key -> !keys.contains(key))
                    .forEach(key -> {
                        PropertyEnum propertyEnum = propertyEnumMap.get(key);

                        if (StringUtils.isBlank(propertyEnum.defaultValue())) {
                            return;
                        }

                        result.put(key, PropertyEnum.convertTo(propertyEnum.defaultValue(), propertyEnum));
                    });

            // Cache the result
            redisService.set(RedisConfig.OPTIONS_KEY, JSON.toJSONString(result));

            return result;
        }

        return JSON.parseObject(resJson, Map.class);
    }

    @Override
    public Map<String, Object> listOptions(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }

        Map<String, Object> optionMap = listOptions();

        Map<String, Object> result = new HashMap<>(keys.size());

        keys.stream()
                .filter(optionMap::containsKey)
                .forEach(key -> result.put(key, optionMap.get(key)));

        return result;
    }

    @Override
    public List<OptionsDTO> listDtos() {
        List<OptionsDTO> result = new LinkedList<>();

        listOptions().forEach((key, value) -> result.add(new OptionsDTO(key, value)));

        return result;
    }


    @Override
    public Object getByKeyOfNonNull(String key) {
        return getByKey(key).orElseThrow(() -> new MissingPropertyException("You have to config " + key + " setting"));
    }

    @Override
    public Optional<Object> getByKey(String key) {
        Assert.hasText(key, "Option key must not be blank");

        return Optional.ofNullable(listOptions().get(key));
    }


    @Override
    public Object getByPropertyOfNonNull(PropertyEnum property) {
        Assert.notNull(property, "Blog property must not be null");

        return getByKeyOfNonNull(property.getValue());
    }

    @Override
    public <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType, T defaultValue) {
        Assert.notNull(property, "Blog property must not be null");

        return getByProperty(property, propertyType).orElse(defaultValue);
    }

    @Override
    public <T> Optional<T> getByProperty(PropertyEnum property, Class<T> propertyType) {
        return getByProperty(property).map(propertyValue -> PropertyEnum.convertTo(propertyValue.toString(), propertyType));
    }

    @Override
    public Optional<Object> getByProperty(PropertyEnum property) {
        Assert.notNull(property, "Blog property must not be null");

        return getByKey(property.getValue());
    }

    @Override
    public <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType) {
        return getByProperty(property, propertyType).orElse(property.defaultValue(propertyType));
    }

    @Override
    public <T extends Enum<T>> T getEnumByPropertyOrDefault(PropertyEnum property, Class<T> valueType, T defaultValue) {
        return getEnumByProperty(property, valueType).orElse(defaultValue);
    }

    @Override
    public <T extends Enum<T>> Optional<T> getEnumByProperty(PropertyEnum property, Class<T> valueType) {
        return getByProperty(property).map(value -> PropertyEnum.convertToEnum(value.toString(), valueType));
    }

    @Override
    public Zone getQnYunZone() {
        return getByProperty(QiniuOssProperties.OSS_ZONE).map(qiniuZone -> {

            Zone zone;
            switch (qiniuZone.toString()) {
                case "z0":
                    zone = Zone.zone0();
                    break;
                case "z1":
                    zone = Zone.zone1();
                    break;
                case "z2":
                    zone = Zone.zone2();
                    break;
                case "na0":
                    zone = Zone.zoneNa0();
                    break;
                case "as0":
                    zone = Zone.zoneAs0();
                    break;
                default:
                    // Default is detecting zone automatically
                    zone = Zone.autoZone();
            }
            return zone;

        }).orElseGet(Zone::autoZone);
    }

    private void publishOptionUpdatedEvent() {
        eventPublisher.publishEvent(new OptionsUpdatedEvent(this));
    }
}
