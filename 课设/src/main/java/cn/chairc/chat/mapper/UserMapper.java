package cn.chairc.chat.mapper;

import cn.chairc.chat.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User getUserBySid(String sid);

    void userAdd(User user);

    void userUpdate(User user);

    void userDelete(String uid);

    void userLoginUpdate(String uid, String ip, String browser);

    List<User> getUserListByAdmin();

    User getUserByAdmin(String sid);
}
