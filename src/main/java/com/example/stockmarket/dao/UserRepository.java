package com.example.stockmarket.dao;

import com.example.stockmarket.entity.User;
import com.example.stockmarket.filter.UserFilter;

import java.util.List;

public interface UserRepository {
    void clear();
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(long id);
    User buyCurrency(User user, double count, Object object);
    User SaleCurrency(User user, double count, Object object);
    User valuationCostUser(User user, Object object);
    User balance(User user, Object object);
    User depositingFunds(User user, double count, Object object);
    int getUsersCount(UserFilter userFilter);
}
