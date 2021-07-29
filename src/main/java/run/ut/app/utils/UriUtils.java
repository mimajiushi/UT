package run.ut.app.utils;

import org.springframework.web.util.UriTemplate;

/**
 * @author chenwenjie.star
 * @date 2021/7/29 11:32 上午
 */
public class UriUtils {

    public static String stringFormat(String uri, Object...params) {
        return new UriTemplate(uri).expand(params).toString();
    }
}
