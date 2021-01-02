package cn.chairc.chat.service.Impl;

import cn.chairc.chat.mapper.PermissionMapper;
import cn.chairc.chat.mapper.UserMapper;
import cn.chairc.chat.model.ChatPage;
import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.model.User;
import cn.chairc.chat.service.PermissionService;
import cn.chairc.chat.service.UserService;
import cn.chairc.chat.util.Tools;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class); //slf4j

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public User getUserBySid(String sid) {
        return userMapper.getUserBySid(sid);
    }

    @Override
    public ResultSet userLogin(String account, String password, HttpServletRequest request) {
        ResultSet resultSet = new ResultSet();
        //1.获取Shiro的Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装数据
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        //3.执行登陆方法
        try {
            subject.login(token);
            //Shiro记录session
            Session session = subject.getSession();
            session.setAttribute("sid", account);
            User user = userService.getUserBySid(account);
            //记录uid，学生姓名
            session.setAttribute("uid", user.getUid());
            session.setAttribute("username", user.getUsername());
            //记录用户权限
            String permission = permissionService.getUserPermission(user.getUid());
            session.setAttribute("permission", permission);
            //更新用户登录记录
            userMapper.userLoginUpdate(user.getUid(), Tools.getUserIp(request), Tools.getBrowserVersion(request));
            log.info(String.format("登录用户为:%s  用户uid为:%s  用户学号为:%s  权限为:%s",
                    user.getUsername(), user.getUid(), user.getSid(), permission));
            log.info("登录成功");
            resultSet.success("登录成功");
        } catch (UnknownAccountException e) {
            //用户名不存在
            log.info("登录失败，用户名不存在");
            resultSet.fail("登录失败，用户名不存在");
        } catch (IncorrectCredentialsException e) {
            //密码错误
            log.info("登录失败，密码错误");
            resultSet.fail("登录失败，密码错误");
        }
        return resultSet;
    }

    @Override
    public ResultSet userAdd(User user) {
        ResultSet resultSet = new ResultSet();
        try {
            userMapper.userAdd(user);
            permissionMapper.userPermissionAdd(user.getUid(),"user:user");
            resultSet.success("添加用户成功");
            log.info("添加用户成功");
        } catch (Exception e) {
            resultSet.fail("添加用户失败");
            log.error("添加用户失败");
        }
        return resultSet;
    }

    @Override
    public ResultSet userUpdate(User user) {
        ResultSet resultSet = new ResultSet();
        try{
            userMapper.userUpdate(user);
            resultSet.success("更新用户成功");
            log.info("更新用户成功");
        }catch (Exception e){
            resultSet.fail("更新用户失败");
            log.error("更新用户失败");
        }
        return resultSet;
    }

    @Override
    public ResultSet userDelete(String uid) {
        ResultSet resultSet = new ResultSet();
        try{
            userMapper.userDelete(uid);
            resultSet.success("删除用户成功");
            log.info("删除用户成功");
        }catch (Exception e){
            resultSet.fail("删除用户失败");
            log.error("删除用户失败");
        }
        return resultSet;
    }

    @Override
    public ResultSet getUserAllByAdmin(int pageNum) {
        ResultSet resultSet = new ResultSet();
        ChatPage chatPage = new ChatPage();
        Page<User> userPages = PageHelper.startPage(pageNum, 10);
        HashMap<String, Object> hashMap = new HashMap<>();
        List<User> userList = userMapper.getUserListByAdmin();
        chatPage.setPage(pageNum);
        chatPage.setTotal((int) ((userPages.getTotal() - 1) / 10 + 1));
        hashMap.put("data", userList);
        hashMap.put("pageNum", chatPage);
        resultSet.success("成员列表返回成功");
        resultSet.setData(hashMap);
        return resultSet;
    }

    @Override
    public ResultSet getUserByAdmin(String sid) {
        ResultSet resultSet = new ResultSet();
        try {
            User user = userMapper.getUserBySid(sid);
            resultSet.success("成员查找成功");
            resultSet.setData(user);
        }catch (Exception e){
            resultSet.fail("成员查找成功失败");
        }
        return resultSet;
    }


}
