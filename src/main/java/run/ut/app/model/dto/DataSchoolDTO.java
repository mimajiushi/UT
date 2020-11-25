package run.ut.app.model.dto;

import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.domain.DataSchool;
import run.ut.app.model.dto.base.OutputConverter;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSchoolDTO implements OutputConverter<DataSchoolDTO, DataSchool>, Serializable {
    private Integer id;

    private String name;

    private Integer provinceId;

    private String level;

    private String website;

    private String abbreviation;

}
