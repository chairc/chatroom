package cn.chairc.chat.shiro;

import cn.chairc.chat.model.Permission;
import cn.chairc.chat.model.Role;
import cn.chairc.chat.model.User;
import cn.chairc.chat.service.PermissionService;
import cn.chairc.chat.service.RoleService;
import cn.chairc.chat.service.UserService;
import cn.chairc.chat.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    private static Logger log = LoggerFactory.getLogger(UserRealm.class); //slf4j

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 执行授权逻辑
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //资源授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //资源授权perms中的字符串

        //Subject subject = SecurityUtils.getSubject();
        //User user = (User) subject.getPrincipal();
        User user = (User) principals.getPrimaryPrincipal();
        //Role role = roleService.getUserRole(user.getRole());
        //获取用户身份许可
        String permission = permissionService.getUserPermission(user.getUid());

        //log.info("当前用户信息为：" + user);
        //log.info("当前用户等级为：" + role.getRole_name());
        log.info("当前用户权限为：" + permission);
        //根据许可名过滤权限（ShiroConfig.java权限过滤器）
        simpleAuthorizationInfo.addStringPermission(permission);

        return simpleAuthorizationInfo;
    }

    /**
     * 执行认证逻辑
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //判断用户名
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        log.info("当前正在登录用户为:"+usernamePasswordToken.getUsername());
        //获取该用户的所有信息
        User user = userService.getUserBySid(usernamePasswordToken.getUsername());

        if (user == null) {
            return null;
        }
        //判断密码
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
