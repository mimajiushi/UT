package run.ut.app.model.support;

import javax.validation.GroupSequence;

/**
 * All check for hibernate validation.
 *
 * @author wenjie
 */
@GroupSequence({CreateCheck.class, UpdateCheck.class})
public interface AllCheck {
}
