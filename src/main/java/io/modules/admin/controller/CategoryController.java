
package io.modules.admin.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.modules.admin.entity.CategoryEntity;
import io.modules.admin.service.CategoryService;
import io.common.utils.PageUtils;
import io.common.utils.R;



/**
 * 
 * @author
 * 
 * 
 */
@Api(tags = "管理端——分类管理")
@RestController
@RequestMapping("admin/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @RequiresPermissions("admin:category:list")
    @ApiOperation("分类列表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }



    @GetMapping("/info/{cateId}")
    @RequiresPermissions("admin:category:info")
    @ApiOperation("分类详情")
    public R info(@PathVariable("cateId") Integer cateId){
		CategoryEntity category = categoryService.getById(cateId);

        return R.ok().put("category", category);
    }


    @PostMapping("/save")
    @RequiresPermissions("admin:category:save")
    @ApiOperation("分类保存")
    public R save(@RequestBody CategoryEntity category){

		categoryService.saveCategory(category);

        return R.ok();
    }


    @PostMapping("/update")
    @RequiresPermissions("admin:category:update")
    @ApiOperation("分类修改")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }


    @PostMapping("/delete")
    @RequiresPermissions("admin:category:delete")
    @ApiOperation("分类删除")
    public R delete(@RequestBody Integer[] cateIds){
		categoryService.deleteByIdList(Arrays.asList(cateIds));

        return R.ok();
    }

}
