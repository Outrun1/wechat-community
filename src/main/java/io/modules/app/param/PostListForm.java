package io.modules.app.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "帖子列表分页")
public class PostListForm {

    @ApiModelProperty(value = "topicId")
    private Integer topicId;

    @ApiModelProperty(value = "page")
    private Integer page;

    @ApiModelProperty(value = "order排序")
    private String order;

    @ApiModelProperty(value = "uid")
    private Integer uid;

    @ApiModelProperty(value = "disId")
    private Integer disId;

    @ApiModelProperty(value = "分类id")
    private Integer classId;


}
