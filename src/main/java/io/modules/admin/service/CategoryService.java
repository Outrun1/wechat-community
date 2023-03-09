
package io.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.PageUtils;
import io.modules.admin.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveCategory(CategoryEntity category);

    void deleteByIdList(List<Integer> list);

    List<CategoryEntity> getByResId(Integer restaurantId);
}

