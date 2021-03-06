package cn.chairc.chat.shiro;

import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static Logger log = LoggerFactory.getLogger(ShiroConfig.class); //slf4j

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /*
         * Shiro过滤器
         * anon:无需认证访问
         * authc：必须认证访问
         * user:使用rememberMe功能直接访问
         * perms:资源必须得到资源权限访问
         * role:资源必须得到角色授权访问
         */
        //filterMap过滤器Map，顺序存放拦截url
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        //放行页面
        filterMap.put("/login", "anon");

        //授权过滤器
        filterMap.put("/user","perms[user:user]");
        filterMap.put("/admin/*","perms[user:admin]");

        //权限过滤器
        filterMap.put("/user/*", "authc");
        //filterMap.put("/superAdmin/*", "authc");

        //设置未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        //失败后跳转登陆页面
        shiroFilterFactoryBean.setLoginUrl("/login");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);


        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1000 * 3600);    // 会话过期时间
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        return defaultWebSessionManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }


    @Bean(name = "userRealm")
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        //cookie设置为7天
        simpleCookie.setMaxAge(604800);
        cookieRememberMeManager.setCookie(simpleCookie);

        return cookieRememberMeManager;
    }
}
