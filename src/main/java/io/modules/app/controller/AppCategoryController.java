package io.modules.app.controller;

import io.common.utils.R;
import io.modules.admin.entity.CategoryEntity;
import io.modules.admin.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * 
 */
@Api(tags = "用户端——分类")
@RestController
@RequestMapping("app/topic")
public class AppCategoryController {


    @Autowired
    private CategoryService categoryService;


    @GetMapping("/classList")
    @ApiOperation("分类列表")
    public R classList(){
        List<CategoryEntity> list = categoryService.list();
        return R.ok().put("result",list);
    }

    @GetMapping("/foodList")
    @ApiOperation("菜品列表")
    public R foodList(@RequestParam Integer restaurantId){
        List<CategoryEntity> list = categoryService.getByResId(restaurantId);
        return R.ok().put("result",list);
    }
}
