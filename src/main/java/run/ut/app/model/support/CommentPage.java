package run.ut.app.model.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * PostComment page implementation.
 *
 * @author wenjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPage<T> {

    private long total;

    private List<T> rows;

}
