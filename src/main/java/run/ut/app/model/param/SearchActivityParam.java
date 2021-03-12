package run.ut.app.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author wenjie
 */

@Data
public class SearchActivityParam implements Serializable {

    @ApiModelProperty(value = "分类id")
    private String classifyId;

    @ApiModelProperty(value = "标题", notes = "模糊搜索")
    private String title;

    @ApiModelProperty(value = "查询者uid", notes = "前端传入token即可，此字段由后端填充")
    private Long operatorUid;

    @ApiModelProperty(value = "排序", notes = "create_time则最近发布的排在最前，appointment_count则预约人数最多的排在最前")
    public String order;

    public enum OrderEnum {

        CREATE_TIME("create_time"),
        APPOINTMENT_COUNT("appointment_count");

        private String column;

        OrderEnum(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }

        public static OrderEnum getByColumn(String column) {
            return Arrays.stream(values()).filter(e -> e.getColumn().equals(column)).findFirst().orElse(null);
        }
    }
}
