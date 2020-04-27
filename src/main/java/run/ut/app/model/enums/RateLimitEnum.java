package run.ut.app.model.enums;

/**
 * @author wenjie
 */
public enum RateLimitEnum {

    /**
     * M/N means that only M times can be requested in N time units
     */
    RRLimit_1_5("1/5"),
    RRLimit_1_60("1/60"),;

    private String limit;

    RateLimitEnum(final String limit) {
        this.limit = limit;
    }

    public String limit() {
        return this.limit;
    }
}
