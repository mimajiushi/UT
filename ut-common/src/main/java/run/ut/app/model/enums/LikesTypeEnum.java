package run.ut.app.model.enums;

import java.io.Serializable;

/**
 * 点赞类型枚举
 *
 * @author wenjie
 * @date 2020-5-16
 */
public enum  LikesTypeEnum implements Serializable {

    LIKE_POST(),
    UN_LIKE_POST(),
    LIKE_COMMENT(),
    UN_LIKE_COMMENT()

}
