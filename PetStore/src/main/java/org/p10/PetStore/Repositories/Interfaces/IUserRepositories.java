package org.p10.PetStore.Repositories.Interfaces;

import org.p10.PetStore.Models.User;

import java.util.List;

public interface IUserRepositories {
    int insertUser(User user);
    User getUser(String userName);
    User updateUser(User user);
    String deleteUser(String userName);
    List<User> getNewestUsers(int limit);
}
