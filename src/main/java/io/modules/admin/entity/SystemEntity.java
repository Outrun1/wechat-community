
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
@TableName("system")
public class SystemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String config;
	/**
	 * 
	 */
	private String value;
	/**
	 * 
	 */
	private String extend;
	/**
	 * 
	 */
	private String intro;

}
