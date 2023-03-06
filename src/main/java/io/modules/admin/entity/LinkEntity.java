
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
@TableName("link")
public class LinkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 路径
	 */
	private String url;
	/**
	 * 图片
	 */
	private String img;
	/**
	 * 3圈子页轮播图
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
