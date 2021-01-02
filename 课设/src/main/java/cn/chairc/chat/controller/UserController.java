package cn.chairc.chat.controller;

import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.model.User;
import cn.chairc.chat.service.UserService;
import cn.chairc.chat.util.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/loginUserByAjax")
    @ResponseBody
    public ResultSet login(@RequestParam(value = "account") String account,
                           @RequestParam(value = "password") String password,
                           HttpServletRequest request) {
        return userService.userLogin(account, password, request);
    }

    @RequestMapping("/addUserByAjax")
    @ResponseBody
    public ResultSet addUserByAjax(@RequestParam(value = "sid") String sid,
                                   @RequestParam(value = "username") String username,
                                   @RequestParam(value = "sex") String sex,
                                   HttpServletRequest request) {
        User user = new User();
        user.setUid(Tools.CreateUserRandomPrivateId());
        user.setSid(sid);
        user.setUsername(username);
        user.setPassword(sid);
        user.setSex(sex);
        user.setIp(Tools.getUserIp(request));
        user.setBrowser(Tools.getBrowserVersion(request));
        user.setWeight("1");
        return userService.userAdd(user);
    }

    @RequestMapping("/updateUserByAjax")
    @ResponseBody
    public ResultSet updateUserByAjax(@RequestParam(value = "uid") String uid,
                                      @RequestParam(value = "sid") String sid,
                                      @RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "sex") String sex,
                                      @RequestParam(value = "ip") String ip,
                                      @RequestParam(value = "browser") String browser,
                                      @RequestParam(value = "weight") String weight) {
        User user = new User();
        user.setUid(uid);
        user.setSid(sid);
        user.setUsername(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setIp(ip);
        user.setBrowser(browser);
        user.setWeight(weight);
        return userService.userUpdate(user);
    }

    @RequestMapping("/deleteUserByAjax")
    @ResponseBody
    public ResultSet deleteUserByAjax(@RequestParam(value = "uid") String uid) {

        return userService.userDelete(uid);
    }

    @RequestMapping("/showUserList")
    @ResponseBody
    public ResultSet showUserList(@RequestParam(value = "pageNum") int pageNum) {
        return userService.getUserAllByAdmin(pageNum);
    }

    @RequestMapping("/showUser")
    @ResponseBody
    public ResultSet showUser(@RequestParam(value = "sid") String sid){
        return userService.getUserByAdmin(sid);
    }

}
