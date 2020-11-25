package run.ut.app.model.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * PostComment page implementation.
 *
 * @author wenjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPage<T> implements Serializable {

    private long total;

    private List<T> rows;

    public static <T> CommentPage<T> emptyPage() {
        return new CommentPage<>(0, new ArrayList<>());
    }
}
