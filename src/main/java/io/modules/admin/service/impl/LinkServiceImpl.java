
package io.modules.admin.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.common.utils.PageUtils;
import io.common.utils.Query;

import io.modules.admin.dao.LinkDao;
import io.modules.admin.entity.LinkEntity;
import io.modules.admin.service.LinkService;


@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkDao, LinkEntity> implements LinkService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LinkEntity> page = this.page(
                new Query<LinkEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}