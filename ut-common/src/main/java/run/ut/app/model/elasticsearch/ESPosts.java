package run.ut.app.model.elasticsearch;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;
import run.ut.app.model.domain.BaseEntity;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.param.PostParam;

import java.time.LocalDateTime;

/**
 * <p>
 * Posts
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "posts")
@Component
public class ESPosts implements InputConverter<PostParam> {

    @Id
    private Long id;

    private Long uid;

    @Field(name = "forum_id")
    private Long forumId;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String title;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String content;

    private Long likes;

    /**
     * Create time.
     */
    @Field(name = "create_time", type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * Update time.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(name = "update_time", type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
