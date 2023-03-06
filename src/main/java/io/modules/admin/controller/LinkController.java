
package io.modules.admin.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.modules.admin.entity.LinkEntity;
import io.modules.admin.service.LinkService;
import io.common.utils.PageUtils;
import io.common.utils.R;



/**
 * 管理端——轮播图管理
 *
 * @author
 * 
 * 
 */
@Api(tags = "管理端——轮播图管理")
@RestController
@RequestMapping("admin/link")
public class LinkController {
    @Autowired
    private LinkService linkService;


    @GetMapping("/list")
    @RequiresPermissions("admin:link:list")
    @ApiOperation("轮播图列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = linkService.queryPage(params);

        return R.ok().put("page", page);
    }



    @GetMapping("/info/{id}")
    @RequiresPermissions("admin:link:info")
    @ApiOperation("轮播图信息")
    public R info(@PathVariable("id") Integer id){
		LinkEntity link = linkService.getById(id);

        return R.ok().put("link", link);
    }


    @PostMapping("/save")
    @RequiresPermissions("admin:link:save")
    @ApiOperation("轮播图保存")
    public R save(@RequestBody LinkEntity link){
		linkService.save(link);

        return R.ok();
    }


    @PostMapping("/update")
    @RequiresPermissions("admin:link:update")
    @ApiOperation("轮播图修改")
    public R update(@RequestBody LinkEntity link){
		linkService.updateById(link);

        return R.ok();
    }


    @PostMapping("/delete")
    @RequiresPermissions("admin:link:delete")
    @ApiOperation("轮播图删除")
    public R delete(@RequestBody Integer[] ids){
		linkService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
