package net.itw.wcms.x27.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.itw.wcms.x27.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    
}
