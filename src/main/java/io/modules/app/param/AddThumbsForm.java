package io.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "评论点赞")
public class AddThumbsForm {

    @ApiModelProperty(value = "评论id")
    private Integer id;

}
