package run.ut.app.model.enums;

import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * PostQuerySortEnum
 *
 * @author wenjie
 */
@Getter
public enum PostQuerySortEnum implements Serializable {
    CREATE_TIME(1, "create_time"),
    UPDATE_TIME(2, "update_time"),
    LIKES(3, "likes");

    private int type;

    private String column;


    PostQuerySortEnum(int type, String column) {
        this.type = type;
        this.column = column;
    }


    public static PostQuerySortEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static PostQuerySortEnum getByName(String column) {
        return Arrays.stream(values()).filter(e -> e.getColumn().equalsIgnoreCase(column)).findFirst().orElse(null);
    }
}
