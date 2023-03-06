
package io.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.AppPageUtils;
import io.common.utils.PageUtils;
import io.modules.admin.entity.CommentEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer getCountByTopicId(Integer id);

    void deleteByAdmin(List<Long> longs);

    Integer getCountByPostId(Integer id);

    AppPageUtils queryCommentPage(Integer postId, Integer page);

    Integer getYesterdayCount();

    Integer getAllCount();
}

