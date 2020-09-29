package run.ut.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import run.ut.app.exception.MissingPropertyException;
import run.ut.app.handler.MyZone;
import run.ut.app.model.domain.Options;
import run.ut.app.model.dto.OptionsDTO;
import run.ut.app.model.param.OptionsParam;
import run.ut.app.model.properties.PropertyEnum;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *  OptionsService
 * </p>
 *
 * @author wenjie
 * @since 2020-03-10
 */
public interface OptionsService extends IService<Options> {

    int DEFAULT_POST_PAGE_SIZE = 10;

    int DEFAULT_COMMENT_PAGE_SIZE = 10;

    int DEFAULT_RSS_PAGE_SIZE = 20;

    /**
     * Save multiple options
     *
     * @param options options
     */
    @Transactional
    void save(@Nullable Map<String, Object> options);

    /**
     * Save multiple options
     *
     * @param optionParams option params
     */
    @Transactional
    void save(@Nullable List<OptionsParam> optionParams);

    /**
     * Save single option.
     *
     * @param optionParam option param
     */
    void save(@Nullable OptionsParam optionParam);

    /**
     * Update option by id.
     *
     * @param optionId    option id must not be null.
     * @param optionParam option param must not be null.
     */
    void update(@NonNull Integer optionId, @NonNull OptionsParam optionParam);

    /**
     * Saves a property.
     *
     * @param property must not be null
     * @param value    could be null
     */
    @Transactional
    void saveProperty(@NonNull PropertyEnum property, @Nullable String value);

    /**
     * Saves blog properties.
     *
     * @param properties blog properties
     */
    @Transactional
    void saveProperties(@NonNull Map<? extends PropertyEnum, String> properties);

    /**
     * Get all options
     *
     * @return Map
     */
    @NonNull
    @Transactional
    Map<String, Object> listOptions();
    /**
     * Lists options by key list.
     *
     * @param keys key list
     * @return a map of option
     */
    @NonNull
    Map<String, Object> listOptionsToMap(@Nullable List<String> keys);


    List<OptionsDTO> listDtos();

    List<OptionsDTO> listDtos(@Nullable List<String> keys);

    /**
     * Gets option value of non null.
     *
     * @param key option key must not be null
     * @return option value of non null
     */
    @NonNull
    Object getByKeyOfNonNull(@NonNull String key);

    /**
     * Get option by key
     *
     * @param key option key must not be blank
     * @return an optional option value
     */
    @NonNull
    Optional<Object> getByKey(@NonNull String key);

    /**
     * Gets option value by blog property.
     *
     * @param property blog property
     * @return an optiona value
     * @throws MissingPropertyException throws when property value dismisses
     */
    @NonNull
    Object getByPropertyOfNonNull(@NonNull PropertyEnum property);

    /**
     * Gets option value by blog property.
     *
     * @param property blog property must not be null
     * @return an optional option value
     */
    @NonNull
    Optional<Object> getByProperty(@NonNull PropertyEnum property);

    /**
     * Gets property value by blog property.
     * <p>
     * Default value from property default value.
     *
     * @param property     blog property must not be null
     * @param propertyType property type must not be null
     * @param <T>          property type
     * @return property value
     */
    <T> T getByPropertyOrDefault(@NonNull PropertyEnum property, @NonNull Class<T> propertyType);

    /**
     * Gets property value by blog property.
     *
     * @param property     blog property must not be null
     * @param propertyType property type must not be null
     * @param defaultValue default value
     * @param <T>          property type
     * @return property value
     */
    <T> T getByPropertyOrDefault(@NonNull PropertyEnum property, @NonNull Class<T> propertyType, T defaultValue);

    /**
     * Gets property value by blog property.
     *
     * @param property     blog property must not be null
     * @param propertyType property type must not be null
     * @param <T>          property type
     * @return property value
     */
    <T> Optional<T> getByProperty(@NonNull PropertyEnum property, @NonNull Class<T> propertyType);

    @Nullable
    <T extends Enum<T>> T getEnumByPropertyOrDefault(@NonNull PropertyEnum property, @NonNull Class<T> valueType, @Nullable T defaultValue);

    <T extends Enum<T>> Optional<T> getEnumByProperty(PropertyEnum property, Class<T> valueType);

    /**
     * Get qi niu yun zone.
     *
     * @return qiniu zone
     */
    @NonNull
    MyZone getQnYunZone();
}

