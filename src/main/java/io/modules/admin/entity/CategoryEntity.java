
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
@TableName("category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId
	private Integer cateId;
	/**
	 * 分类名称
	 */
	private String cateName;
	/**
	 * 是否推荐(1推荐)
	 */
	private Integer isTop;
	/**
	 * 图片
	 */
	private String coverImage;

}
