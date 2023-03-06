
package io.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.PageUtils;
import io.modules.admin.entity.LinkEntity;

import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface LinkService extends IService<LinkEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

