package run.ut.app.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * Id Utils
 *
 * @author chenwenjie.star
 * @date 2021/4/25 4:26 下午
 */
public class IdUtils {

    /**
     * snowflake id by hutool
     *
     * @return id
     */
    public static long snowflakeId() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextId();
    }

}
