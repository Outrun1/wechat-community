
package io.modules.admin.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.common.exception.CommunityException;
import io.common.vo.*;
import io.common.utils.*;
import io.modules.admin.entity.PostEntity;
import io.modules.admin.entity.SystemEntity;
import io.modules.admin.service.*;
import io.modules.app.dao.FollowDao;
import io.modules.app.entity.FollowEntity;
import io.modules.app.param.*;
import io.modules.app.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.modules.admin.dao.AppUserDao;
import io.modules.admin.entity.AppUserEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;


@Service
@Slf4j
public class AppUserServiceImpl extends ServiceImpl<AppUserDao, AppUserEntity> implements AppUserService {


    @Autowired
    private PostService postService;

    @Autowired
    private AppUserDao userDao;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private FollowService followService;

    @Autowired
    private FollowDao followDao;

    @Autowired
    private SystemService systemService;

    @Autowired
    private CommentService commentService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AppUserEntity> queryWrapper = new QueryWrapper<>();
        //模糊查询
        String key = (String) params.get("key");
        if (!ObjectUtil.isEmpty(key)) {
            queryWrapper.like("username", key)
                    .or()
                    .like("mobile", key);
        }
        queryWrapper.lambda().orderByDesc(AppUserEntity::getUid);
        IPage<AppUserEntity> page = this.page(
                new Query<AppUserEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    public void ban(Integer id) {
        Integer status = this.lambdaQuery().eq(AppUserEntity::getUid, id).one().getStatus();
        if (status.equals(Constant.USER_BANNER)) {
            throw new CommunityException("该用户已被禁用");
        }
        this.lambdaUpdate()
                .set(AppUserEntity::getStatus, 1)
                .eq(AppUserEntity::getUid, id)
                .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void openBan(Integer id) {
        Integer status = this.lambdaQuery().eq(AppUserEntity::getUid, id).one().getStatus();
        if (status.equals(Constant.USER_NORMAL)) {
            throw new CommunityException("该用户已解除禁用");
        }
        boolean update = this.lambdaUpdate()
                .set(AppUserEntity::getStatus, 0)
                .eq(AppUserEntity::getUid, id)
                .update();
        if(!update){
            throw new CommunityException("解除失败");
        }
    }

    @Override
    public HomeRateResponse indexDate() {
        String today = cn.hutool.core.date.DateUtil.date().toString("yyyy-MM-dd");
        String yesterday = cn.hutool.core.date.DateUtil.yesterday().toString("yyyy-MM-dd");
        Integer postCount = postService.lambdaQuery().select(PostEntity::getId).count();
        HomeRateResponse response = new HomeRateResponse();
        response.setTotalPostOfReview(0);
        response.setTotalPost(postCount);
        response.setNewUserNum(this.getRegisterNumByDate(today));
        response.setYesterdayNewUserNum(this.getRegisterNumByDate(yesterday));
        response.setTotalUser(this.getTotalNum());
        response.setYesterdayCommentCount(commentService.getYesterdayCount());
        response.setCommentCount(commentService.getAllCount());
        return response;
    }

    @Override
    public Integer smsLogin(SmsLoginForm form, HttpServletRequest request) {
        AppUserEntity appUserEntity = this.lambdaQuery().eq(AppUserEntity::getMobile, form.getMobile()).one();
        String codeKey = "code_" + form.getMobile();
        String s = redisUtils.get(codeKey);
        if (!s.equals(form.getCode())) {
            throw new CommunityException("验证码错误");
        }
        if (ObjectUtil.isNotNull(appUserEntity)) {
            //登录
            if (appUserEntity.getStatus() == 1) {
                throw new CommunityException("该账户已被禁用");
            }
            return appUserEntity.getUid();
        } else {
            //注册
            AppUserEntity appUser = new AppUserEntity();
            appUser.setMobile(form.getMobile());
            appUser.setGender(0);
            appUser.setAvatar(Constant.DEAULT_HEAD);
            appUser.setUsername("" + RandomUtil.randomNumbers(8));
            appUser.setCreateTime(DateUtil.nowDateTime());
            appUser.setUpdateTime(DateUtil.nowDateTime());
            List<String> list = new ArrayList<>();
            list.add("新人");
            appUser.setTagStr(JSON.toJSONString(list));
            baseMapper.insert(appUser);
            AppUserEntity user = this.lambdaQuery().eq(AppUserEntity::getMobile, form.getMobile()).one();
            return user.getUid();
        }


    }

    @Override
    public String sendSmsCode(SendCodeForm param) {
        String code = RandomUtil.randomNumbers(6);
        String codeKey = "code_" + param.getMobile();
        String s = redisUtils.get(codeKey);
        if (ObjectUtil.isNotNull(s)) {
            return s;
        }
        redisUtils.set(codeKey, code, 60 * 5);
        return code;
    }

    @Override
    public AppUserResponse getUserInfo(AppUserEntity user) {

        AppUserResponse response = new AppUserResponse();
        BeanUtils.copyProperties(user, response);
        Integer follow = followService.getFollowCount(user.getUid());
        Integer fans = followService.getFans(user.getUid());
        Integer postNum = postService.getPostNumByUid(user.getUid());
        response.setFans(fans);
        response.setPostNum(postNum);
        response.setFollow(follow);
        return response;
    }

    @Override
    public void updateAppUserInfo(AppUserUpdateForm appUserUpdateForm, AppUserEntity user) {
        if (!ObjectUtil.isEmpty(appUserUpdateForm.getAvatar())) {
            user.setAvatar(appUserUpdateForm.getAvatar());
        }
        if(!ObjectUtil.isEmpty(appUserUpdateForm.getGender())){
            user.setGender(appUserUpdateForm.getGender());
        }
        baseMapper.updateById(user);
        redisUtils.delete("userId:" + user.getUid());
    }

    @Override
    public void addFollow(AddFollowForm request, AppUserEntity user) {
        if (request.getId().equals(user.getUid())) {
            throw new CommunityException("不能关注自己哦");
        }
        boolean isFollow = followService.isFollowOrNot(user.getUid(), request.getId());
        if (isFollow) {
            throw new CommunityException("不要重复关注哦");
        }
        FollowEntity followEntity = new FollowEntity();
        followEntity.setUid(user.getUid());
        followEntity.setFollowUid(request.getId());
        followEntity.setCreateTime(DateUtil.nowDateTime());
        followService.save(followEntity);
        //TODO 消息通知
    }

    @Override
    public void cancelFollow(AddFollowForm request, AppUserEntity user) {
        followDao.cancelFollow(user.getUid(), request.getId());
    }

    @Override
    public AppPageUtils userFans(Integer currPage, Integer uid) {
        List<Integer> uidList = followService.getFansList(uid);
        if (uidList.isEmpty()) {
            return new AppPageUtils(null, 0, 10, currPage);
        }
        Page<AppUserEntity> page = new Page<>(currPage, 10);
        QueryWrapper<AppUserEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().in(AppUserEntity::getUid, uidList);
        Page<AppUserEntity> page1 = this.page(page, queryWrapper1);

        AppPageUtils pages = new AppPageUtils(page1);
        List<?> data = pages.getData();
        List<TopicUserResponse> responseList = new ArrayList<>();
        data.forEach(l -> {
            TopicUserResponse topicUserResponse = new TopicUserResponse();
            BeanUtils.copyProperties(l, topicUserResponse);
            Integer follow = followService.isFollow(uid, topicUserResponse.getUid());
            topicUserResponse.setHasFollow(follow);
            responseList.add(topicUserResponse);
        });
        pages.setData(responseList);
        return pages;
    }

    @Override
    public AppPageUtils follow(Integer currPage, AppUserEntity user) {
        List<Integer> followUids = followService.getFollowUids(user);
        if (followUids.isEmpty()) {
            return new AppPageUtils(null, 0, 10, currPage);
        }
        Page<AppUserEntity> page = new Page<>(currPage, 10);
        QueryWrapper<AppUserEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().in(AppUserEntity::getUid, followUids);
        Page<AppUserEntity> page1 = this.page(page, queryWrapper1);


        AppPageUtils pages = new AppPageUtils(page1);
        List<?> data = pages.getData();
        List<TopicUserResponse> responseList = new ArrayList<>();
        data.forEach(l -> {
            TopicUserResponse topicUserResponse = new TopicUserResponse();
            BeanUtils.copyProperties(l, topicUserResponse);
            Integer follow = followService.isFollow(user.getUid(), topicUserResponse.getUid());
            topicUserResponse.setHasFollow(follow);
            responseList.add(topicUserResponse);
        });
        pages.setData(responseList);
        return pages;
    }

    @Override
    public AppUserInfoResponse findUserInfoById(Integer uid, AppUserEntity user) {
        AppUserEntity userEntity = this.getById(uid);
        if(ObjectUtil.isNull(userEntity)){
            throw new CommunityException("用户不存在");
        }
        AppUserInfoResponse response = new AppUserInfoResponse();
        BeanUtils.copyProperties(userEntity, response);
        return response;
    }

    @Override
    public Integer miniWxLogin(WxLoginForm form) {

        String openId = getOpenId(form.getCode());
        if(io.common.utils.ObjectUtil.isEmpty(openId)){
            throw new CommunityException("请正确配置appId和密钥");
        }
        //根据openId获取数据库信息 判断用户是否登录
        AppUserEntity user = this.lambdaQuery().eq(AppUserEntity::getOpenid, openId).one();
        if (ObjectUtil.isNotNull(user)) {
            if (user.getStatus() == 1) {
                throw new CommunityException("该账户已被禁用");
            }
            //其他业务todo
            return user.getUid();
        } else {
            //新注册用户
            AppUserEntity appUser = new AppUserEntity();
            appUser.setGender(0);
            appUser.setAvatar(Constant.DEAULT_HEAD);
            appUser.setUsername("wx" + RandomUtil.randomNumbers(8));
            appUser.setCreateTime(DateUtil.nowDateTime());
            appUser.setUpdateTime(DateUtil.nowDateTime());
            appUser.setOpenid(openId);
            List<String> list = new ArrayList<>();
            list.add("新人");
            appUser.setTagStr(JSON.toJSONString(list));
            baseMapper.insert(appUser);
            AppUserEntity users = this.lambdaQuery().eq(AppUserEntity::getOpenid, openId).one();
            return users.getUid();
        }
    }

    @Override
    public List<AppUserRankResponse> userRank() {
        DateTime month = cn.hutool.core.date.DateUtil.beginOfMonth(new Date());

        List<PostEntity> postList = postService.lambdaQuery().gt(PostEntity::getCreateTime, month).list();
        if(postList.isEmpty()){
            return new ArrayList<>();
        }
        Map<Integer, Long> collect = postList.stream().collect(Collectors.groupingBy(PostEntity::getUid, Collectors.counting()));
        Map<Integer, Long> sorted = collect
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        List<AppUserRankResponse> list=new ArrayList<>();
        sorted.forEach((k,v)->{
            AppUserRankResponse response=new AppUserRankResponse();
            BeanUtils.copyProperties(this.getById(k),response);
            response.setPostNumber(v.intValue());
            list.add(response);
        });
        return list;
    }


    private Integer getTotalNum() {
        return this.lambdaQuery().select(AppUserEntity::getUid).count();
    }

    private Integer getRegisterNumByDate(String date) {
        QueryWrapper<AppUserEntity> wrapper = Wrappers.query();
        wrapper.select("uid");
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        return userDao.selectCount(wrapper);
    }

    private String getOpenId(String code){
        SystemEntity system = systemService.lambdaQuery().eq(SystemEntity::getConfig, "miniapp").one();

        //小程序唯一标识   (在微信小程序管理后台获取)
        String appId = system.getValue();
        //小程序的 app secret (在微信小程序管理后台获取)
        String secret = system.getExtend();
        //授权（必填）
        String grant_type = "authorization_code";
        //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        //向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        String params = "appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSON.parseObject(sr);
        //用户的唯一标识（openId）
        return (String) json.get("openid");
    }

}