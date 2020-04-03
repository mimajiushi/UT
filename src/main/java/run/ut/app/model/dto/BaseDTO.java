package run.ut.app.model.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Base entity.
 *
 * @author wenjie
 */
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class BaseDTO {

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
    private int deleted = 0;

}
