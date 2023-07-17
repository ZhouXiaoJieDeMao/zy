package com.bsoft.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bsoft.system.domain.SysDynamicReport;
import com.bsoft.system.service.ISysDynamicReportService;
import com.bsoft.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bsoft.common.constant.Constants;
import com.bsoft.common.core.domain.AjaxResult;
import com.bsoft.common.core.domain.entity.SysMenu;
import com.bsoft.common.core.domain.entity.SysUser;
import com.bsoft.common.core.domain.model.LoginBody;
import com.bsoft.common.utils.SecurityUtils;
import com.bsoft.framework.web.service.SysLoginService;
import com.bsoft.framework.web.service.SysPermissionService;
import com.bsoft.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author fastbuild
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysDynamicReportService iSysDynamicReportService;

    @Autowired
    private ISysNoticeService iSysNoticeService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);

        String version = iSysNoticeService.getMaxNoticeVersion();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("version", version==null?"1":version);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
