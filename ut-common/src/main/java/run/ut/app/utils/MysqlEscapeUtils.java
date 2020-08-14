package run.ut.app.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Mysql dscape run.ut.app.utils
 *
 * @author chenwenjie
 */
public class MysqlEscapeUtils {
    public static String escape(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            String[] fbsArr = {"\\", "$", "|", "%", "_", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }

        return keyword;
    }
}
