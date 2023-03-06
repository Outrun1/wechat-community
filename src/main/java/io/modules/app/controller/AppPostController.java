package io.modules.app.controller;

import cn.hutool.core.util.ObjectUtil;
import io.common.vo.PostDetailResponse;
import io.common.utils.AppPageUtils;
import io.common.utils.R;
import io.common.validator.ValidatorUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.admin.service.PostService;
import io.modules.app.annotation.Login;
import io.modules.app.annotation.LoginUser;
import io.modules.app.param.AddCollectionForm;
import io.modules.app.param.AddCommentForm;
import io.modules.app.param.AddPostForm;
import io.modules.app.param.PostListForm;
import io.modules.app.service.PostCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * 
 */
@Api(tags = "用户端——帖子")
@RestController
@RequestMapping("app/post")
public class AppPostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostCollectionService postCollectionService;



    @GetMapping("/lastPost")
    @ApiOperation("最新动态列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页",paramType = "query", dataType = "Integer", required = true)
    })
    public R lastPost(@RequestParam Integer page){

        AppPageUtils pages =postService.lastPost(page);
        return R.ok().put("result", pages);
    }


    @Login
    @GetMapping("/followUserPost")
    @ApiOperation("获取关注用户帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页",paramType = "query", dataType = "Integer", required = true)
    })
    public R followUserPost(@RequestParam Integer page, @LoginUser AppUserEntity user){

        AppPageUtils pages =postService.followUserPost(page,user);
        if(ObjectUtil.isNull(page)){
            return R.error("您没有关注的用户");
        }
        return R.ok().put("result", pages);
    }



    /**
     * 帖子点赞收藏
     */
    @Login
    @PostMapping("/addCollection")
    @ApiOperation("帖子点赞收藏")
    public R addCollection(@RequestBody AddCollectionForm request, @LoginUser AppUserEntity user){
        postService.addCollection(request,user);

        return R.ok();
    }

    /**
     * 帖子取消点赞收藏
     */
    @Login
    @PostMapping("/cancelCollection")
    @ApiOperation("帖子取消点赞收藏")
    public R cancelCollection(@RequestBody AddCollectionForm request, @LoginUser AppUserEntity user){
        postCollectionService.cancelCollection(request,user);
        return R.ok();
    }

    @Login
    @GetMapping("/myPost")
    @ApiOperation("我的帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页",paramType = "query", dataType = "Integer", required = true)
    })
    public R myPost(@RequestParam("page") Integer page, @LoginUser AppUserEntity user){

        AppPageUtils pages =postService.myPost(page,user);
        return R.ok().put("result", pages);
    }


    @Login
    @GetMapping("/myCollectPost")
    @ApiOperation("我点赞收藏的帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页",paramType = "query", dataType = "Integer", required = true)
    })
    public R myCollectPost(@RequestParam("page") Integer page, @LoginUser AppUserEntity user){

        AppPageUtils pages =postService.myCollectPost(page,user);
        return R.ok().put("result", pages);
    }


    @GetMapping("/detail")
    @ApiOperation("获取帖子详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子id",paramType = "query", dataType = "Integer", required = true)
    })
    public R detail(@RequestParam Integer id){

        PostDetailResponse response=postService.detail(id);
        return R.ok().put("result", response);
    }


    @Login
    @PostMapping("/addComment")
    @ApiOperation("添加评论")
    public R addComment(@RequestBody AddCommentForm request, @LoginUser AppUserEntity user){
        ValidatorUtils.validateEntity(request);
        postService.addComment(request,user);

        return R.ok();
    }

    @Login
    @PostMapping("/addPost")
    @ApiOperation("发帖子")
    public R addPost(@RequestBody AddPostForm request, @LoginUser AppUserEntity user){
        ValidatorUtils.validateEntity(request);
        Integer id=postService.addPost(request,user);
        if(id==0){
            return R.error("发帖失败");
        }
        return R.ok().put("result",id);
    }

    @Login
    @PostMapping("/list")
    @ApiOperation("帖子列表分页")
    public R list(@RequestBody PostListForm request, @LoginUser AppUserEntity user){

        AppPageUtils page = postService.queryPageList(request,user);

        return R.ok().put("result", page);
    }
}