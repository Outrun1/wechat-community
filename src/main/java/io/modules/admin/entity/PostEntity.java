
package io.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("post")
public class PostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer uid;
	/**
	 * 圈子id
	 */
	private Integer topicId;
	/**
	 * 话题id
	 */
	private Integer discussId;
	/**
	 * 投票id
	 */
	private Integer voteId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 文件
	 */
	private String media;
	/**
	 * 浏览量
	 */
	private Integer readCount;
	/**
	 * 置顶
	 */
	private Integer postTop;
	/**
	 * 帖子类型：1 图文 ，2视频 ，3文章，4投票
	 */
	private Integer type;
	/**
	 * 地址名称
	 */
	private String address;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 分类id
	 */
	private Integer cut;
}
