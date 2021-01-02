package cn.chairc.chat.service.Impl;

import cn.chairc.chat.mapper.PermissionMapper;
import cn.chairc.chat.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    private static Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class); //slf4j

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 用户-权限表获取权限
     */
    @Override
    public String getUserPermission(String uid) {
        return permissionMapper.getUserPermissionByUid(uid);
    }
}
