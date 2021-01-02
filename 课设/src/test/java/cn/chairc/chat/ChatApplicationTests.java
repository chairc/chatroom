package cn.chairc.chat;

import cn.chairc.chat.mapper.UserMapper;
import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.model.User;
import cn.chairc.chat.service.UserService;
import cn.chairc.chat.util.Tools;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ChatApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class ChatApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Test
    void randomPrivateIdGenerator() {
        for (int i = 0; i < 10; i++) {
            String uid = Tools.CreateUserRandomPrivateId();
            System.out.println(uid);
        }
    }

    @Test
    void addUser() {
        String sid = "123", username = "张三", sex = "男", ip = "127.0.0.1", browser = "xx";
        User user = new User();
        user.setUid(Tools.CreateUserRandomPrivateId());
        user.setSid(sid);
        user.setUsername(username);
        user.setPassword(sid);
        user.setSex(sex);
        user.setIp(ip);
        user.setBrowser(browser);
        user.setWeight("1");
        ResultSet resultSet = userService.userAdd(user);
        System.out.print(resultSet);
    }

    @Test
    void updateLogin(){
        userMapper.userLoginUpdate("user537037459605","1","123");
    }

    @Test
    void test1(){
        User user = userMapper.getUserBySid("20202202994");
        System.out.print(user.toString());
    }
}
