package cn.chairc.chat.controller;

import cn.chairc.chat.util.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private static Logger log = LoggerFactory.getLogger(IndexController.class); //slf4j

    @RequestMapping("/")
    public String showIndexPage() {
        log.info("主页重定向登录页已加载...");
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String showHomePage() {
//        String username = Tools.sessionValidate("username");
//        String uid = Tools.sessionValidate("uid");
//        String sid = Tools.sessionValidate("sid");
        String permission = Tools.sessionValidate("permission");
        if ("user:admin".equals(permission)) {
            return "redirect:/admin";
        } else if ("user:user".equals(permission)) {
            return "redirect:/user";
        }else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/chatroom")
    public String showChatroomPage() {
        log.info("聊天页已加载...");
        return "chatroom";
    }

    @RequestMapping("/login")
    public String showLoginPage() {
        log.info("登录页已加载...");
        return "login";
    }

    @RequestMapping("/user")
    public String showUserPage(Model model) {
        log.info("当前跳转管理员页面");
        model.addAttribute("username",Tools.sessionValidate("username"));
        return "user";
    }

    @RequestMapping("/admin")
    public String showAdminPage(Model model) {
        log.info("当前跳转个人页面");
        model.addAttribute("username1",Tools.sessionValidate("username"));
        return "admin";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

}
