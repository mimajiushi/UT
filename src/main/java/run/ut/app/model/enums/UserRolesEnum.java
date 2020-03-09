package run.ut.app.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * roles enum
 *
 * @author wenjie
 */
@Getter
public enum UserRolesEnum implements IEnum<Integer> {
    ROLE_ADMIN(1, "ROLE_ADMIN", "系统管理员"),
    ROLE_TOURIST(2, "ROLE_TOURIST", "游客"),
    ROLE_USER(3, "ROLE_USER", "正式用户"),
    ROLE_VIP_USER(4, "ROLE_VIP_USER", "VIP"),
    ROLE_TUTOR(5, "ROLE_TUTOR", "导师"),
    ROLE_SPONSOR(5, "ROLE_SPONSOR", "赛事主办方")
    ;

    private int type;

    private String name;

    private String cnName;

    UserRolesEnum(int type, String name, String cnName) {
        this.type = type;
        this.name = name;
        this.cnName = cnName;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }

    public static UserRolesEnum getByType(int type) {
        return Arrays.stream(values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

    public static UserRolesEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }
}
