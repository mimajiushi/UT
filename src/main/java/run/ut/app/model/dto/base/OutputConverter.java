package run.ut.app.model.dto.base;

import org.springframework.lang.NonNull;

import static run.ut.app.utils.BeanUtils.updateProperties;

/**
 * Converter interface for output DTO.
 *
 * <b>The implementation type must be equal to DTO type</b>
 *
 * @param <DTO>    the implementation class type
 * @param <DOMAIN> domain type
 * @author wenjie
 */
public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {

    /**
     * Convert from domain.(shallow)
     *
     * @param domain domain data
     * @return converted dto data
     */
    @SuppressWarnings("unchecked")
    @NonNull
    default <T extends DTO> DTO convertFrom(@NonNull DOMAIN domain) {

        updateProperties(domain, this);

        return (DTO) this;
    }
}
