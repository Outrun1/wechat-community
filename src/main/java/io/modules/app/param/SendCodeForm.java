package io.modules.app.param;

/**
 * @author
 * 
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "手机验证码")
public class SendCodeForm {

    @ApiModelProperty(value = "手机号")
    private String mobile;

}
