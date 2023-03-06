package io.modules.app.entity;

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
@TableName("comment_thumbs")
public class CommentThumbsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 评论id
	 */
	private Integer cId;
	/**
	 * 用户id
	 */
	private Integer uid;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
