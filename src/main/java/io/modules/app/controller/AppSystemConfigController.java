package io.modules.app.controller;

import io.common.utils.R;
import io.modules.admin.entity.SystemEntity;
import io.modules.admin.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * 
 */
@Api(tags = "用户端——系统配置")
@RestController
@RequestMapping("app/system")
public class AppSystemConfigController {

    @Autowired
    private SystemService systemService;

    @ApiOperation("logo配置")
    @GetMapping("/miniConfig")
    public R miniConfig(){
        SystemEntity system = systemService.lambdaQuery()
                .eq(SystemEntity::getConfig, "miniapp")
                .one();
        return R.ok().put("logo",system.getIntro());
    }

}
