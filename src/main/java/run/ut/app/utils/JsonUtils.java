package run.ut.app.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Json utilities.
 *
 * @author wenjie
 */
@Component
public class JsonUtils implements ApplicationContextAware {

    /**
     * Default json mapper.
     */
    public static ObjectMapper objectMapper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ObjectUtils.isEmpty(applicationContext)) {
            throw new ApplicationContextException("applicationContext must not be null");
        }
        JsonUtils.objectMapper = applicationContext.getBean(ObjectMapper.class);
    }

    private JsonUtils() {
    }


    /**
     * Creates a default json mapper.
     *
     * @return object mapper
     */
    public static ObjectMapper createDefaultJsonMapper() {
        return createDefaultJsonMapper(null);
    }

    /**
     * Creates a default json mapper.
     *
     * @param strategy property naming strategy
     * @return object mapper
     */
    @NonNull
    public static ObjectMapper createDefaultJsonMapper(@Nullable PropertyNamingStrategy strategy) {
        // Create object mapper
        ObjectMapper mapper = new ObjectMapper();
        // Configure
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Set property naming strategy
        if (strategy != null) {
            mapper.setPropertyNamingStrategy(strategy);
        }
        return mapper;
    }

    /**
     * Converts json to the object specified type.
     *
     * @param json json content must not be blank
     * @param type object type must not be null
     * @param <T>  target object type
     * @return object specified type
     */
    @NonNull
    public static <T> T jsonToObject(@NonNull String json, @NonNull Class<T> type) {
        return jsonToObject(json, type, objectMapper);
    }

    /**
     * Converts json to the object specified type.
     *
     * @param json         json content must not be blank
     * @param type         object type must not be null
     * @param objectMapper object mapper must not be null
     * @param <T>          target object type
     * @return object specified type
     */
    @NonNull
    public static <T> T jsonToObject(@NonNull String json, @NonNull Class<T> type, @NonNull ObjectMapper objectMapper) {
        Assert.hasText(json, "Json content must not be blank");
        Assert.notNull(type, "Target type must not be null");
        Assert.notNull(objectMapper, "Object mapper must not null");

        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    /**
//     * Converts input stream to object specified type.
//     *
//     * @param inputStream input stream must not be null
//     * @param type        object type must not be null
//     * @param <T>         target object type
//     * @return object specified type
//     * @throws IOException throws when fail to convert
//     */
//    @NonNull
//    public static <T> T inputStreamToObject(@NonNull InputStream inputStream, @NonNull Class<T> type) throws IOException {
//        return inputStreamToObject(inputStream, type, null);
//    }
//
//    /**
//     * Converts input stream to object specified type.
//     *
//     * @param inputStream  input stream must not be null
//     * @param type         object type must not be null
//     * @param objectMapper object mapper must not be null
//     * @param <T>          target object type
//     * @return object specified type
//     * @throws IOException throws when fail to convert
//     */
//    @NonNull
//    public static <T> T inputStreamToObject(@NonNull InputStream inputStream, @NonNull Class<T> type, @NonNull ObjectMapper objectMapper) throws IOException {
//        Assert.notNull(inputStream, "Input stream must not be null");
//
//        String json = IOUtils.toString(inputStream);
//        return jsonToObject(json, type, objectMapper);
//    }

    /**
     * Converts object to json format.
     *
     * @param source source object must not be null
     * @return json format of the source object
     */
    @NonNull
    public static String objectToJson(@NonNull Object source) {
        return objectToJson(source, objectMapper);
    }

    /**
     * Converts object to json format.
     *
     * @param source       source object must not be null
     * @param objectMapper object mapper must not be null
     * @return json format of the source object
     */
    @NonNull
    public static String objectToJson(@NonNull Object source, @NonNull ObjectMapper objectMapper) {
        Assert.notNull(source, "Source object must not be null");
        Assert.notNull(objectMapper, "Object mapper must not null");

        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a map to the object specified type.
     *
     * @param sourceMap source map must not be empty
     * @param type      object type must not be null
     * @param <T>       target object type
     * @return the object specified type
     */
    @NonNull
    public static <T> T mapToObject(@NonNull Map<String, ?> sourceMap, @NonNull Class<T> type) {
        return mapToObject(sourceMap, type, objectMapper);
    }

    /**
     * Converts a map to the object specified type.
     *
     * @param sourceMap    source map must not be empty
     * @param type         object type must not be null
     * @param objectMapper object mapper must not be null
     * @param <T>          target object type
     * @return the object specified type
     */
    @NonNull
    public static <T> T mapToObject(@NonNull Map<String, ?> sourceMap, @NonNull Class<T> type, @NonNull ObjectMapper objectMapper) {
        Assert.notEmpty(sourceMap, "Source map must not be empty");

        // Serialize the map
        String json = objectToJson(sourceMap, objectMapper);

        // Deserialize the json format of the map
        return jsonToObject(json, type, objectMapper);
    }

    /**
     * Converts a source object to a map
     *
     * @param source source object must not be null
     * @return a map
     */
    @NonNull
    public static Map<?, ?> objectToMap(@NonNull Object source) {
        return objectToMap(source, objectMapper);
    }

    /**
     * Converts a source object to a map
     *
     * @param source       source object must not be null
     * @param objectMapper object mapper must not be null
     * @return a map
     */
    @NonNull
    public static Map<?, ?> objectToMap(@NonNull Object source, @NonNull ObjectMapper objectMapper) {

        // Serialize the source object
        String json = objectToJson(source, objectMapper);

        // Deserialize the json
        return jsonToObject(json, Map.class, objectMapper);
    }

    /**
     * Converts json to List(default as ArrayList)
     *
     * @param json    json content must not be blank
     * @param type    object type must not be null
     * @param <T>     target object type
     * @return        a list
     */
    @NonNull
    public static <T> List<T> jsonToList(@NonNull String json, @NonNull Class<T> type) {

        Assert.notNull(type, "Type must not be null");

        return jsonToCollection(json, type, ArrayList.class, objectMapper);
    }

    /**
     * Converts json to Collection
     *
     * @param json            json content must not be blank
     * @param type            object type must not be null
     * @param collectionType  type must be extends Collection
     * @param <T>             target object type
     * @return                a list
     */
    @NonNull
    public static <T> List<T> jsonToCollection(@NonNull String json, @NonNull Class<T> type, @NonNull Class<? extends Collection> collectionType, @NonNull ObjectMapper objectMapper) {

        Assert.notNull(type, "Type must not be null");
        Assert.notNull(collectionType, "CollectionType must not be null");
        Assert.notNull(objectMapper, "Object mapper must not null");

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(collectionType, type);

        try {
            return objectMapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
