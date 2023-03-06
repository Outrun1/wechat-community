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
@TableName("topic_top")
public class TopicTopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 圈子id
	 */
	private Integer topicId;
	/**
	 * 内容id
	 */
	private Integer postId;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
