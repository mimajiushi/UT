package run.ut.app.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * roles enum
 *
 * @author wenjie
 */
@Getter
@ToString
public enum UserRolesEnum implements IEnum<Integer>, Serializable {
    ROLE_ADMIN(8, "ROLE_ADMIN", "系统管理员"),
    ROLE_TOURIST(0, "ROLE_TOURIST", "游客"),
    ROLE_STUDENT(1, "ROLE_STUDENT", "学生"),
    ROLE_TUTOR(2, "ROLE_TUTOR", "导师"),
    ROLE_SPONSOR(4, "ROLE_SPONSOR", "活动主办方");

    private int type;

    private String name;

    @JsonValue
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

    public static List<String> getRoles(int roleId) {
        ArrayList<String> roles = new ArrayList<>();
        if (roleId == 0) {
            roles.add(ROLE_TOURIST.getCnName());
        }
        if ((roleId & 1) == 1) {
            roles.add(ROLE_STUDENT.getCnName());
        }
        if ((roleId & 2) == 2) {
            roles.add(ROLE_TUTOR.getCnName());
        }
        if ((roleId & 4) == 4) {
            roles.add(ROLE_SPONSOR.getCnName());
        }
        if ((roleId & 8) == 8) {
            roles.add(ROLE_ADMIN.getCnName());
        }
        return roles;
    }
}
