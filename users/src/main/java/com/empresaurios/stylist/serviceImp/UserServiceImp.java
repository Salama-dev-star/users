package com.empresaurios.stylist.serviceImp;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.empresaurios.stylist.bean.PaginatedResponse;
import com.empresaurios.stylist.bean.Users;
import com.empresaurios.stylist.repository.UserRepository;
import com.empresaurios.stylist.service.UserService;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserServiceImp implements UserService{


     @Inject
    UserRepository userRepository;

    @Inject
    EntityManager em; 

    // 1. Create User/sing in (hashing)
    public Users createUser(Users user) {
        em.persist(user);
        user.setPassword( BcryptUtil.bcryptHash(user.getPassword()));
        return user;
    }

    // 2. SignUp (validation or hashing)
    @Transactional
    public Users signUp(Users user) {
        // TODO: Hash password if needed
        return createUser(user);
    }

    // 3. Find by Email
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 5. Delete User
    public boolean deleteUser(Long id) {
        return Users.deleteById(id);
    }
    
    // 6. Filter Users
    public List<Users> filterUsers(String email,String password) {
        return userRepository.filter(email,password);
    }   

    // 7. List all
    public PaginatedResponse<Users> listAll(int page) {
        
        Page p = new Page( page - 1, 3);
        return new PaginatedResponse<>( Users.findAll(Sort.descending("createdAt")).page(p));
        
    }

    @Override
    public boolean singin(Users user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'singin'");
    }

    @Override
    public Users updateUsers(String email,Users user) {
        
        Optional<Users> userActual= findByEmail(email);
        if (userActual.isPresent()){
            userActual.get().setEmail(user.getEmail());
            userActual.get().setName(user.getName());
            userActual.get().setPassword(user.getPassword());
            userActual.get().setRole(user.getRole());
            userActual.get().setSurnames(user.getSurnames());
            userRepository.persist(userActual.get());

            return userActual.get();
        }

        throw new NoSuchElementException("NO SE ENCUENTRA EL USUARIO");

    }
}
