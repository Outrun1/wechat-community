package io.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * 
 * @author
 * 
 * 
 */
@Data
@TableName("post_collection")
public class PostCollectionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
//	@TableId
	private Integer uid;
	/**
	 * 帖子id
	 */
	private Integer postId;

}
