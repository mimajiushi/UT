package run.ut.app.model.enums;

/**
 * Post Permalink type enum.
 *
 * @author wenjie
 */
public enum PostPermalinkType implements ValueEnum<Integer> {

    /**
     * /archives/${url}
     */
    DEFAULT(0),

    /**
     * /1970/01/01/${url}
     */
    DATE(1),

    /**
     * /1970/01/${url}
     */
    DAY(2),

    /**
     * /?p=${id}
     */
    ID(3);

    private Integer value;

    PostPermalinkType(Integer value) {
        this.value = value;
    }


    @Override
    public Integer getValue() {
        return value;
    }
}
