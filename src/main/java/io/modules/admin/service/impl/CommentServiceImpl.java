
package io.modules.admin.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.common.utils.Constant;
import io.common.vo.AppCommentResponse;
import io.common.utils.AppPageUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.admin.service.AppUserService;
import io.modules.app.service.CommentThumbsService;
import io.modules.app.utils.LocalUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.common.utils.PageUtils;
import io.common.utils.Query;

import io.modules.admin.dao.CommentDao;
import io.modules.admin.entity.CommentEntity;
import io.modules.admin.service.CommentService;
import org.springframework.transaction.annotation.Transactional;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private LocalUser localUser;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private CommentThumbsService commentThumbsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<CommentEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(CommentEntity::getId);
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public Integer getCountByTopicId(Integer id) {
        return baseMapper.selectCount(new LambdaQueryWrapper<CommentEntity>()
                .eq(CommentEntity::getStatus, Constant.COMMENT_NORMAL)
                .eq(CommentEntity::getPostId, id));
    }


    /**
     * 管理端批量删除评论
     * @param list
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByAdmin(List<Long> list) {
        list.forEach(id->{
            this.removeById(id);
            //子评论更改展示状态为屏蔽
            this.lambdaUpdate()
                    .set(CommentEntity::getStatus,  Constant.COMMENT_DOWN)
                    .eq(CommentEntity::getPid, id)
                    .update();
        });
    }


    @Override
    public Integer getCountByPostId(Integer id) {
        return this.lambdaQuery()
                .eq(CommentEntity::getStatus,  Constant.COMMENT_NORMAL)
                .eq(CommentEntity::getPostId, id).count();
    }

    @Override
    public AppPageUtils queryCommentPage(Integer postId, Integer page) {
        Page<CommentEntity> commentPage = new Page<>(page,10);
        QueryWrapper<CommentEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(CommentEntity::getPostId,postId);
        queryWrapper.lambda().eq(CommentEntity::getStatus, Constant.COMMENT_NORMAL);
        Page<CommentEntity> pages = baseMapper.selectPage(commentPage,queryWrapper);
        AppPageUtils appPage=new AppPageUtils(pages);
        List<CommentEntity> data = (List<CommentEntity>) appPage.getData();
        List<AppCommentResponse> responseList=new ArrayList<>();
        AppUserEntity user = localUser.getUser();
        data.forEach(l->{
            if(l.getPid()==0){
                AppCommentResponse response=new AppCommentResponse();
                BeanUtils.copyProperties(l,response);
                response.setUserInfo(appUserService.getById(response.getUid()));
                response.setThumbs(commentThumbsService.getThumbsCount(l.getId()));
                if(user==null){
                    response.setIsThumbs(false);
                }else{
                    response.setIsThumbs(commentThumbsService.isThumbs(user.getUid(),l.getId()));
                }
                responseList.add(response);
            }

        });
        appPage.setData(responseList);
        return appPage;
    }

    /**
     * 获取昨天评论数
     * @return
     */
    @Override
    public Integer getYesterdayCount() {
        DateTime yesterday = DateUtil.yesterday();
        return this.lambdaQuery()
                .ge(CommentEntity::getCreateTime,yesterday)
                .eq(CommentEntity::getStatus,  Constant.COMMENT_NORMAL)
                .count();
    }

    @Override
    public Integer getAllCount() {
        return this.lambdaQuery()
                .eq(CommentEntity::getStatus, Constant.COMMENT_NORMAL)
                .count();
    }

}