package run.ut.app.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import run.ut.app.model.enums.SexEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenjie
 * @since 2020-03-08
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
@ApiModel(value="User对象", description="")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    private String openid;

    private String password;

    private String nickname;

    @JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private SexEnum sex;

    private String avatar;

    private String phoneNumber;

    private String email;

    private String description;

}
