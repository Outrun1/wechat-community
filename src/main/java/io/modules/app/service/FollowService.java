package io.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.PageUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.app.entity.FollowEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface FollowService extends IService<FollowEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer getFollowCount(Integer uid);

    Integer getFans(Integer uid);

    boolean isFollowOrNot(Integer uid, Integer id);

    List<Integer> getFollowUid(AppUserEntity user);

    List<Integer> getFollowUids(AppUserEntity user);

    Integer isFollow(Integer uid,Integer followUid);

    List<Integer> getFansList(Integer uid);
}

