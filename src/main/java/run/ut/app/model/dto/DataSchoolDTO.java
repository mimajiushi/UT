package run.ut.app.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import run.ut.app.model.domain.DataSchool;
import run.ut.app.model.dto.base.OutputConverter;

@Data
@ToString
@EqualsAndHashCode
public class DataSchoolDTO implements OutputConverter<DataSchoolDTO, DataSchool> {
    private Integer id;

    private String name;

    private Integer provinceId;

    private String level;

    private String website;

    private String abbreviation;

}
