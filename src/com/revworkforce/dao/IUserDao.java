package com.revworkforce.dao;

import  com.revworkforce.model.Users;

public interface IUserDao
{
    boolean createUser(Users user);

    Users getUserByEmail(String email);

    Users getUserById(int userId);

    boolean updateUserStatus(int userId, boolean isActive);

    boolean updateLastLogin(int userId);

    Users getUserForLogin(String email);

    boolean updatePassword(int userId, String passwordHash);
}