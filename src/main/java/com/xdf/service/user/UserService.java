package com.xdf.service.user;

import com.xdf.bean.Users;
import com.xdf.service.IBaseService;

public interface UserService extends IBaseService<Users> {
    String validateName(String userName);

    Users login(String userName, String password);
}
