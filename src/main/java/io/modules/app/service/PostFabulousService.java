package io.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.PageUtils;
import io.modules.app.entity.PostFabulousEntity;

import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface PostFabulousService extends IService<PostFabulousEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean isThumb(Integer uid, Integer id);
}

