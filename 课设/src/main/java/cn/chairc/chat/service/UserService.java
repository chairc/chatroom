package cn.chairc.chat.service;

import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User getUserBySid(String sid);

    ResultSet userLogin(String account, String password, HttpServletRequest request);

    ResultSet userAdd(User user);

    ResultSet userUpdate(User user);

    ResultSet userDelete(String uid);

    ResultSet getUserAllByAdmin(int pageNum);

    ResultSet getUserByAdmin(String sid);

}
