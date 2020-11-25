package run.ut.app.model.param;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base param.
 *
 * @author wenjie
 */
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class BaseParam implements Serializable {

    /**
     * Create time.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * Update time.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * Delete flag.
     */
    @TableLogic
    private Integer deleted;

}
