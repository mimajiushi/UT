package run.ut.app.utils;

import cn.hutool.core.util.ObjectUtil;

import java.lang.reflect.Field;

/**ObjectUtil
 * @author wenjie
 */
public class ObjectUtils {
    public static String allfieldIsNotNUll(Object o) {
        try {
            for (Field field:o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object object = field.get(o);
                if (ObjectUtil.isEmpty(object)) {
                    return field.getName();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
