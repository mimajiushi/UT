package run.ut.app.model.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.DataAreaLevelEnum;

import java.io.Serializable;

/**
 * <p>
 * 行政区域数据表
 * </p>
 *
 * @author wenjie
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataArea对象", description = "行政区域数据表")
public class DataArea implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "父级ID")
    private Integer parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简称")
    private String shortName;

    @ApiModelProperty(value = "经度")
    private Float longitude;

    @ApiModelProperty(value = "纬度")
    private Float latitude;

    @ApiModelProperty(value = "等级(1省/直辖市,2地级市,3区县,4镇/街道)")
    @JsonSerialize(using = ToStringSerializer.class)
    private DataAreaLevelEnum level;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态(0启用/1禁用)")
    private Integer status;



}
