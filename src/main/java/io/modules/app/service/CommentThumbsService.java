package io.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.PageUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.app.entity.CommentThumbsEntity;
import io.modules.app.param.AddThumbsForm;

import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface CommentThumbsService extends IService<CommentThumbsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean isThumbs(Integer uid, Long id);

    Integer getThumbsCount(Long id);

    void addThumbs(AddThumbsForm request, AppUserEntity user);

    void cancelThumbs(AddThumbsForm request, AppUserEntity user);
}

