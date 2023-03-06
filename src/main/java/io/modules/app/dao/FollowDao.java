package io.modules.app.dao;

import io.modules.app.entity.FollowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author
 * 
 * 
 */
@Mapper
public interface FollowDao extends BaseMapper<FollowEntity> {

	void cancelFollow(@Param("uid")Integer uid, @Param("followId")Integer followId);
}
