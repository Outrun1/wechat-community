
package io.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.vo.PostDetailResponse;
import io.common.utils.AppPageUtils;
import io.common.utils.PageUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.admin.entity.PostEntity;
import io.modules.app.param.*;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface PostService extends IService<PostEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteByAdmin(List<Integer> integers);

    Integer getPostNumByUid(Integer uid);

    AppPageUtils lastPost(Integer page);

    AppPageUtils followUserPost(Integer page, AppUserEntity user);

    void addCollection(AddCollectionForm request, AppUserEntity user);

    AppPageUtils myPost(Integer page, AppUserEntity user);

    AppPageUtils myCollectPost(Integer page,AppUserEntity user);

    PostDetailResponse detail(Integer id);

    void addComment(AddCommentForm request, AppUserEntity user);

    Integer addPost(AddPostForm request, AppUserEntity user);

    AppPageUtils queryPageList(PostListForm request, AppUserEntity user);
}

