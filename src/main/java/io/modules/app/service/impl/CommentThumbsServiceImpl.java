package io.modules.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.common.exception.CommunityException;
import io.common.utils.DateUtil;
import io.modules.admin.entity.AppUserEntity;
import io.modules.app.param.AddThumbsForm;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.common.utils.PageUtils;
import io.common.utils.Query;

import io.modules.app.dao.CommentThumbsDao;
import io.modules.app.entity.CommentThumbsEntity;
import io.modules.app.service.CommentThumbsService;


@Service("commentThumbsService")
public class CommentThumbsServiceImpl extends ServiceImpl<CommentThumbsDao, CommentThumbsEntity> implements CommentThumbsService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentThumbsEntity> page = this.page(
                new Query<CommentThumbsEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 是否点赞
     * @param uid
     * @param id
     * @return
     */
    @Override
    public Boolean isThumbs(Integer uid, Long id) {
        CommentThumbsEntity one = baseMapper.selectOne(new LambdaQueryWrapper<CommentThumbsEntity>()
                .eq(CommentThumbsEntity::getCId, id)
                .eq(CommentThumbsEntity::getUid, uid));

        return Optional.ofNullable(one).isPresent();
    }

    @Override
    public Integer getThumbsCount(Long id) {
        return this.lambdaQuery()
                .eq(CommentThumbsEntity::getCId, id)
                .count();
    }

    /**
     * 点赞
     * @param request
     * @param user
     */
    @Override
    public void addThumbs(AddThumbsForm request, AppUserEntity user) {
        CommentThumbsEntity one=baseMapper.selectOne(new LambdaQueryWrapper<CommentThumbsEntity>()
        .eq(CommentThumbsEntity::getCId,request.getId())
        .eq(CommentThumbsEntity::getUid,user.getUid()));
        if(ObjectUtil.isNotNull(one)){
            throw new CommunityException("请勿重复点赞");
        }
        CommentThumbsEntity ct=new CommentThumbsEntity();
        ct.setUid(user.getUid());
        ct.setCId(request.getId());
        ct.setCreateTime(DateUtil.nowDateTime());
        boolean save = this.save(ct);
        if(!save){
            throw new CommunityException("点赞失败");
        }
    }

    /**
     * 取消点赞
     * @param request
     * @param user
     */
    @Override
    public void cancelThumbs(AddThumbsForm request, AppUserEntity user) {
        baseMapper.delete(new LambdaQueryWrapper<CommentThumbsEntity>()
                .eq(CommentThumbsEntity::getCId,request.getId())
                .eq(CommentThumbsEntity::getUid,user.getUid()));
    }


}