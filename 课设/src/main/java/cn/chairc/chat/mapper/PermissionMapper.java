package cn.chairc.chat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface PermissionMapper {
    String getUserPermissionByUid(String uid);

    void userPermissionAdd(String uid,String permission);
}
