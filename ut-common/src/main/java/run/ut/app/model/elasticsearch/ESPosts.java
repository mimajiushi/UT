package run.ut.app.model.elasticsearch;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.ut.app.model.domain.BaseEntity;
import run.ut.app.model.dto.base.InputConverter;
import run.ut.app.model.param.PostParam;

/**
 * <p>
 * Posts
 * </p>
 *
 * @author wenjie
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "posts")
public class ESPosts extends BaseEntity implements InputConverter<PostParam> {

    @Id
    private Long id;

    private Long uid;

    private Long forumId;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String title;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String content;

    private Long likes;
}
