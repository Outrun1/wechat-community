
package io.modules.admin.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.modules.admin.entity.PostEntity;
import io.modules.admin.service.PostService;
import io.common.utils.PageUtils;
import io.common.utils.R;



/**
 * 
 * 帖子管理
 * @author
 * 
 * 
 */
@Api(tags = "管理端——帖子管理")
@RestController
@RequestMapping("admin/post")
public class PostController {
    @Autowired
    private PostService postService;


    @GetMapping("/list")
    @RequiresPermissions("admin:post:list")
    @ApiOperation("帖子列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = postService.queryPage(params);

        return R.ok().put("page", page);
    }



    @GetMapping("/info/{id}")
    @RequiresPermissions("admin:post:info")
    @ApiOperation("帖子信息")
    public R info(@PathVariable("id") Integer id){
		PostEntity post = postService.getById(id);

        return R.ok().put("post", post);
    }


    @PostMapping("/save")
    @RequiresPermissions("admin:post:save")
    @ApiOperation("帖子保存")
    public R save(@RequestBody PostEntity post){
		postService.save(post);

        return R.ok();
    }


    @PostMapping("/update")
    @RequiresPermissions("admin:post:update")
    @ApiOperation("帖子修改")
    public R update(@RequestBody PostEntity post){
		postService.updateById(post);

        return R.ok();
    }


    @PostMapping("/delete")
    @RequiresPermissions("admin:post:delete")
    @ApiOperation("帖子删除")
    public R delete(@RequestBody Integer[] ids){
        postService.deleteByAdmin(Arrays.asList(ids));
        return R.ok();
    }

}
