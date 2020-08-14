package run.ut.app.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Access token.
 *
 * @author wenjie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthToken {

    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Expired in. (seconds)
     */
    @JsonProperty("expiration_time")
    private Long expirationTime;
}
