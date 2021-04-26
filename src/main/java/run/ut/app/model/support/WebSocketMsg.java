package run.ut.app.model.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * todo 替换成跟数据库有映射关系的类
 *
 * @author wenjie
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMsg implements Serializable {
    private Integer type;

    private Object msg;
}
