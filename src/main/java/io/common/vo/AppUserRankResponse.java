package io.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jl.Yu
 * 
 * 
 */
@Data
public class AppUserRankResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Integer uid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 性别(0未知，1男，2女)
	 */
	private Integer gender;


	/**
	 * 个性签名
	 */
	private String intro;

	/**
	 * 用户标签
	 */
	private String tagStr;

	private Integer postNumber;

}
