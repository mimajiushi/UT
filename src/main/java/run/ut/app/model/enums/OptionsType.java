package run.ut.app.model.enums;

/**
 * Option Type.
 *
 * @author wenjie
 */
public enum OptionsType implements ValueEnum<Integer> {

    /**
     * internal option
     */
    INTERNAL(0),

    /**
     * custom option
     */
    CUSTOM(1);

    private Integer value;

    OptionsType(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
