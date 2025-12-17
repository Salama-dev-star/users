package com.empresaurios.stylist.service;

import com.empresaurios.stylist.bean.PaginatedResponse;
import java.util.List;

import java.util.Optional;

import com.empresaurios.stylist.bean.Users;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public interface UserService {

    // 1. Create User/sing in (hashing)
    public Users createUser(Users user);

    // 2. SignUp (validation or hashing)
    @Transactional
    public Users signUp(Users user);

    // 3. Find by Email
    public Optional<Users> findByEmail(String email);

    // 5. Delete User
    public boolean deleteUser(Long id);
    
    // 6. Filter Users
    public List<Users> filterUsers(String email,String password) ;

    // 7. List all
    public PaginatedResponse<Users> listAll(int page);

    public boolean singin(Users user);

    public Users updateUsers(String email, Users user);
}
