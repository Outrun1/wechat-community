package io.modules.admin.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.modules.admin.entity.AppUserEntity;
import io.modules.admin.service.AppUserService;
import io.common.utils.PageUtils;
import io.common.utils.R;



/**
 * 
 * 管理端——会员管理
 * @author
 * 
 * 
 */
@Api(tags = "管理端——会员管理")
@RestController
@RequestMapping("admin/user")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;


    @GetMapping("/list")
    @RequiresPermissions("admin:user:list")
    @ApiOperation("用户列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appUserService.queryPage(params);

        return R.ok().put("page", page);
    }



    @GetMapping("/info/{uid}")
    @RequiresPermissions("admin:user:info")
    @ApiOperation("用户详情")
    public R info(@PathVariable("uid") Integer uid){
		AppUserEntity user = appUserService.getById(uid);

        return R.ok().put("user", user);
    }


    @PostMapping("/save")
    @RequiresPermissions("admin:user:save")
    @ApiOperation("用户保存")
    public R save(@RequestBody AppUserEntity user){
		appUserService.save(user);

        return R.ok();
    }


    @PostMapping("/update")
    @RequiresPermissions("admin:user:update")
    @ApiOperation("用户修改")
    public R update(@RequestBody AppUserEntity user){
		appUserService.updateById(user);

        return R.ok();
    }


    @PostMapping("/delete")
    @RequiresPermissions("admin:user:delete")
    @ApiOperation("用户修改")
    public R delete(@RequestBody Integer[] uids){
		appUserService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }


    @PostMapping("/ban/{id}")
    @RequiresPermissions("admin:user:update")
    @ApiOperation("用户禁用")
    public R ban(@PathVariable("id") Integer id){
		appUserService.ban(id);

        return R.ok();
    }


    @PostMapping("/openBan/{id}")
    @RequiresPermissions("admin:user:update")
    @ApiOperation("用户解除禁用")
    public R openBan(@PathVariable("id") Integer id){
		appUserService.openBan(id);

        return R.ok();
    }

}
