
package io.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.common.vo.AppUserInfoResponse;
import io.common.vo.AppUserRankResponse;
import io.common.vo.AppUserResponse;
import io.common.vo.HomeRateResponse;
import io.common.utils.AppPageUtils;
import io.common.utils.PageUtils;
import io.modules.admin.entity.AppUserEntity;
import io.modules.app.param.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author
 * 
 * 
 */
public interface AppUserService extends IService<AppUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void ban(Integer id);

    void openBan(Integer id);

    /**
     * 首页数据
     * @return HomeRateResponse
     */
    HomeRateResponse indexDate();

    Integer smsLogin(SmsLoginForm form, HttpServletRequest request);

    String sendSmsCode(SendCodeForm param);

    AppUserResponse getUserInfo(AppUserEntity user);

    void updateAppUserInfo(AppUserUpdateForm appUserUpdateForm, AppUserEntity user);

    void addFollow(AddFollowForm request, AppUserEntity user);

    void cancelFollow(AddFollowForm request, AppUserEntity user);

    AppPageUtils userFans(Integer page, Integer uid);

    AppPageUtils follow(Integer page, AppUserEntity user);

    AppUserInfoResponse findUserInfoById(Integer uid, AppUserEntity user);

    Integer miniWxLogin(WxLoginForm form);

    List<AppUserRankResponse> userRank();
}

