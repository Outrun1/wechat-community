
package io.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.utils.PageUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.admin.entity.SystemEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface SystemService extends IService<SystemEntity> {

    PageUtils queryPage(Map<String, Object> params);


}

