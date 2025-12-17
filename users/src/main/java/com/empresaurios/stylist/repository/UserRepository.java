package com.empresaurios.stylist.repository;

import java.util.List;
import java.util.Optional;

import com.empresaurios.stylist.bean.Users;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<Users>{

     public Optional<Users> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public List<Users> filter(String name, String email) {
        String query = "1=1";
        if (name != null) query += " and lower(name) like concat('%', ?1, '%')";
        if (email != null) query += " and lower(email) like concat('%', ?2, '%')";

        return find(query, name, email).list();
    }
}
