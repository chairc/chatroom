package cn.chairc.chat.service.Impl;

import cn.chairc.chat.model.Role;
import cn.chairc.chat.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class); //slf4j
    @Override
    public Role getUserRole(String role) {
        return null;
    }
}
