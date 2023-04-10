
package io.modules.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.common.exception.CommunityException;
import io.modules.admin.entity.PostEntity;
import io.modules.admin.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.common.utils.PageUtils;
import io.common.utils.Query;

import io.modules.admin.dao.CategoryDao;
import io.modules.admin.entity.CategoryEntity;
import io.modules.admin.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private PostService postService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 分类保存
     *
     * @param category
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(CategoryEntity category) {
        Integer count = this.lambdaQuery().eq(CategoryEntity::getCateName, category.getCateName()).count();
        if (count != 0) {
            throw new CommunityException("分类名不能重复");
        }
        this.save(category);
    }

    /**
     * 删除分类
     *
     * @param list
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdList(List<Integer> list) {
        list.forEach(id -> {
            Integer count = postService.lambdaQuery().eq(PostEntity::getCut, id).count();
            if (count > 0) {
                CategoryEntity category = this.getById(id);
                throw new CommunityException(category.getCateName() + "分类下存在帖子未删除");
            }
        });
        this.removeByIds(list);
    }

    /**
     * 通过餐厅查询菜品
     *
     * @param restaurantId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CategoryEntity> getByResId(Integer restaurantId) {
        QueryWrapper<CategoryEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(CategoryEntity::getRestaurantId,restaurantId);
        return this.list(queryWrapper);
    }
}