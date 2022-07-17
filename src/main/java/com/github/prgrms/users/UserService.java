package com.github.prgrms.users;

public interface UserService {

    public User login(Email email, String password);

    public UserDto findById(Long userId);

}