package com.bsoft.web.controller.system;

import com.alibaba.fastjson2.JSONObject;
import com.bsoft.common.constant.Constants;
import com.bsoft.common.constant.UserConstants;
import com.bsoft.common.core.domain.AjaxResult;
import com.bsoft.common.core.domain.entity.SysUser;
import com.bsoft.common.utils.SecurityUtils;
import com.bsoft.framework.web.service.SysLoginService;
import com.bsoft.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
/***
 * 单点登录Controller*
 * @author sgc
 * */
@Slf4j
@RestController
public class LoginSsoController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysLoginService loginService;

    @PostMapping("/loginSso")
    public AjaxResult loginSso(String userName, String loginStatus) {
        //这里进行单点登录上级系统的令牌校验 写自己的逻辑
//        JSONObject ssoObject = this.checkSsoToken(token);
        //处理结果 {code=xxx,data={xxx},msg=xxx}
//        String code = ssoObject.getString("code");
        String loginName = null;
        AjaxResult ajax = null;
        if (loginStatus.equals("1")) {
            //验证成功需要自动登录
//            JSONObject dataObject = ssoObject.getJSONObject("data");//拿到登录名
            loginName = userName ;//dataObject.getString("userName");
        } else {
            //验证失败返回失败信息
            ajax = AjaxResult.error("登录信息已过期，重新登录");
            return ajax;
        }
        //组装checkUserNameUnique方法需要的SysUser对象，检测用户名存在否
        SysUser loginUser = new SysUser();
        loginUser.setUserName(loginName);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(loginName))) {
            log.info("单点登录用户[{}]已存在.", loginName);
        }
        else {
            log.info("单点登录用户[{}]不存在, 需要创建.", loginName);
            //将上级系统带过来的用户创建个新用户存储起来
//            SysUser sysUser = this.createSsoUser(ssoObject);
//            userService.insertUser(sysUser);
            ajax = AjaxResult.error("当前用户在系统中没有创建！");
            return ajax;
        }
        //生成本系统的令牌给到前端进行登录
        ajax = AjaxResult.success();//这里设置单点登录用户默认密码为123456
        String tokenNew = loginService.loginNoCaptcha(loginName, "$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2", null);
        ajax.put("token", tokenNew);ajax.put("msg", "登录成功");return ajax;
    }
//    //处理父级系统传过来的令牌进行校验返回登录信息结果
//    JSONObject checkSsoToken(String token) {
//        JSONObject jsonObject = new JSONObject();
//        //测试代码
//        jsonObject.put("code","0");
//        jsonObject.put("msg","验证成功");
//        JSONObject jsonObjectData =new JSONObject();
//        jsonObjectData.put("userName","sso1");
//        jsonObjectData.put("nickName","单点1");
//        jsonObject.put("data",jsonObjectData);
//        //测试环境
//        String baseUrl = "http://xxxxx/xxx/check?code=" + token;
//        //根据实际地址进行修改//...//进行逻辑校验...//...
//        return jsonObject;
//    }
//    //组装单点登录的用户对象 将来存入本系统
//    SysUser createSsoUser(JSONObject ssoObject){
//        JSONObject dataObject = ssoObject.getJSONObject("data");
//        //从父级系统拿到的用户信息
//        String userId = dataObject.getString("userId");
//        String companyId = dataObject.getString("companyId");
//        String companyName = dataObject.getString("companyName");
//        String deptId = dataObject.getString("deptId");
//        String userName = dataObject.getString("userName");
//        String nickName = dataObject.getString("nickName");
//        String email = dataObject.getString("email");
//        String phonenumber = dataObject.getString("phonenumber");
//        String sex = dataObject.getString("sex");
//        String avatar = dataObject.getString("avatar");
//        //组装本系统用户信息
//        SysUser sysUser = new SysUser();
//        sysUser.setUserName(userName);
//        sysUser.setNickName(nickName);
//        sysUser.setPassword(SecurityUtils.encryptPassword("123456"));
//        sysUser.setCreateBy("sso");
//        sysUser.setCreateTime(new Date());
//        sysUser.setDeptId(202L);
//        //所属部门 建议在后管新增一个部门进行初始化
//        Long[] roleIds = {100L};
//        sysUser.setRoleIds(roleIds);
//        //归属角色  建议在后管新增一个角色进行初始化
//        return sysUser;
//    }


}