package com.jack.service.impl;

import com.jack.dao.UserMapper;
import com.jack.entity.User;
import com.jack.service.UserService;
import com.jack.util.HBaseUtil;
import com.jack.util.MailUtil;
import com.jack.util.SecurityCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Autowired
    MailUtil mailUtil;

    @Resource
    private HBaseUtil hBaseUtil;

    private final String tableName = "dhxGameUser";

    @Override
    public int login(String email, String password) {
        if (email != null) {
            User user = selectByEmail(email);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                return 2;
            }
        } else {
            return -1;
        }
    }

    @Override
    public int signUp(User user) {
        if (user != null) {
            //查看邮箱是否已被注册
            if (selectByEmail(user.getEmail()) != null) {
                return 2;
            } else {
                int result = userMapper.insert(user);
                user = userMapper.selectByEmail(user.getEmail());
                hBaseUtil.putUser(tableName, user);
                return result;
            }
        } else {
            return -1;
        }
    }

    @Override
    public User selectByEmail(String email) {
        User user = null;

        if (email != null) {

            String flag = hBaseUtil.selectValue(tableName, email, "information", "flag");
            if (flag == null) {
                user = userMapper.selectByEmail(email);
                if (user != null) {
                    hBaseUtil.putUser(tableName, user);
                } else {
                    hBaseUtil.putRecord(tableName, email, "information", "flag", "false");
                }
            } else {
                if (flag.equals("true")) {
                    user = hBaseUtil.selectUser(tableName, email);
                }
            }
        }
        return user;
    }

    @Override
    public int resetPassword(String email, String newPassword) {

        hBaseUtil.updateColumn(tableName, email, "information", "password", newPassword);

        return userMapper.resetPassword(email, newPassword);
    }

    @Override
    public void sendSecurityCode(String email) {
        User user = selectByEmail(email);
        String securityCode = SecurityCodeUtil.getSecurityCode(6);
        if (user != null) {
            hBaseUtil.updateColumn(tableName, email, "securityCode", "resetPassword", securityCode);
            mailUtil.sendTemplateMail(email, user.getUsername(), "重置密码", "您正在执行重置密码操作,", securityCode);
        } else {
            hBaseUtil.updateColumn(tableName, email, "securityCode", "signUp", securityCode);
            mailUtil.sendTemplateMail(email, "用户", "欢迎加入悼红轩游戏", "您正在执行注册操作,", securityCode);
        }
    }

    @Override
    public String getSecurityCode(String email, String type) {
        return hBaseUtil.selectValue(tableName, email, "securityCode", type);
    }
}
