package io.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.PageUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.app.entity.PostCollectionEntity;
import io.modules.app.param.AddCollectionForm;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface PostCollectionService extends IService<PostCollectionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer collectCount(Integer postId);

    Boolean isCollection(Integer uid,Integer postId);

    void cancelCollection(AddCollectionForm request, AppUserEntity user);

    List<Integer> getPostListByUid(Integer uid);
}

