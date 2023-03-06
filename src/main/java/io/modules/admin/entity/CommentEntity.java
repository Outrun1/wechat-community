
package io.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author
 * 
 * 
 */
@Data
@TableName("comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 父级id
	 */
	private Integer pid;
	/**
	 * 评论类型:1帖子
	 */
	private Integer type;
	/**
	 * 评论作者ID
	 */
	private Long uid;
	/**
	 * 被回复用户ID
	 */
	private Integer toUid;
	/**
	 * 评论帖子ID
	 */
	private Long postId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论状态
	 * 0 下架  1正常
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
