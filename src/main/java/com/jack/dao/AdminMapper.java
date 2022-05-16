package com.jack.dao;

import com.jack.entity.Admin;
import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(String adminName);

    int insert(Admin record);

    Admin selectByPrimaryKey(String adminName);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);
}